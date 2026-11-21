package guivnf.losttrinkets.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.item.trinkets.BigFootTrinket;
import guivnf.losttrinkets.item.trinkets.CreepoTrinket;
import guivnf.losttrinkets.item.trinkets.OctopickTrinket;

import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EventHandler {

    private static final Object NONE = new Object();
    private static final ConcurrentMap<Class<?>, Object> STATS_FIELD_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, Object> MAX_STATS_CACHE = new ConcurrentHashMap<>();

    public static void tick(Player player) {
        UnlockHandler.tick(player);
        PlayerData data = LostTrinketsAPI.getData(player);
        if (data.unlockDelay > 0) {
            data.unlockDelay--;
        }
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        trinkets.getTickable().forEach(trinket -> trinket.tick(player.level(), player.blockPosition(), player));
        if (trinkets.isActive(Itms.BOOK_O_ENCHANTING.get())
                && player.containerMenu instanceof EnchantmentMenu em) {
            int target = applyMaxEnchantingStatsIfPresent(em) ? 100 : 30;
            for (int i = 0; i < em.costs.length; i++) {
                if (em.costs[i] > 0 && em.costs[i] < target) {
                    em.costs[i] = target;
                }
            }
        }
    }

    private static boolean applyMaxEnchantingStatsIfPresent(EnchantmentMenu em) {
        Class<?> menuClass = em.getClass();
        if (menuClass == EnchantmentMenu.class) return false;
        Object statsFieldCached = STATS_FIELD_CACHE.computeIfAbsent(menuClass, EventHandler::findEnchantingStatsField);
        if (statsFieldCached == NONE) return false;
        Field statsField = (Field) statsFieldCached;
        Object maxStatsCached = MAX_STATS_CACHE.computeIfAbsent(statsField.getType(), EventHandler::buildMaxEnchantingStats);
        if (maxStatsCached == NONE) return false;
        try {
            if (statsField.get(em) != maxStatsCached) {
                statsField.set(em, maxStatsCached);
            }
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static Object findEnchantingStatsField(Class<?> menuClass) {
        for (Class<?> k = menuClass; k != null && k != EnchantmentMenu.class && k != Object.class; k = k.getSuperclass()) {
            for (Field f : k.getDeclaredFields()) {
                Class<?> ft = f.getType();
                if ("stats".equals(f.getName())
                        && ft.isRecord()
                        && "EnchantmentTableStats".equals(ft.getSimpleName())) {
                    try {
                        f.setAccessible(true);
                        return f;
                    } catch (Throwable ignored) {
                        return NONE;
                    }
                }
            }
        }
        return NONE;
    }

    private static Object buildMaxEnchantingStats(Class<?> statsClass) {
        try {
            RecordComponent[] components = statsClass.getRecordComponents();
            Class<?>[] paramTypes = new Class<?>[components.length];
            Object[] args = new Object[components.length];
            for (int i = 0; i < components.length; i++) {
                paramTypes[i] = components[i].getType();
                args[i] = maxValueFor(components[i].getType());
            }
            return statsClass.getDeclaredConstructor(paramTypes).newInstance(args);
        } catch (Throwable ignored) {
            return NONE;
        }
    }

    private static Object maxValueFor(Class<?> t) {
        if (t == float.class) return Float.MAX_VALUE;
        if (t == int.class) return Integer.MAX_VALUE;
        if (t == boolean.class) return true;
        if (Set.class.isAssignableFrom(t)) return Collections.emptySet();
        return null;
    }

    public static void onLivingUpdate(LivingEntity entity) {
        if (entity instanceof Mob mob) {
            LivingEntity target = mob.getTarget();
            if (target == null) {
                target = TargetHandler.getBrainMemorySafe(mob.getBrain(), MemoryModuleType.ATTACK_TARGET).orElse(null);
            }
            if (target instanceof Player owner) {
                activeTrinkets(owner).forEach(trinket -> trinket.onTargeted(owner, mob));
            }
        }
        TargetHandler.onLivingUpdate(entity);
    }

    public static boolean onExplosionStart(Entity exploder) {
        return CreepoTrinket.preventExplosion(exploder);
    }

    public static boolean joinWorld(Entity entity) {
        if (OctopickTrinket.collectDrops(entity))
            return true;
        BigFootTrinket.addAvoidGoal(entity);
        return false;
    }

    public static boolean onAttack(LivingEntity entity, DamageSource source) {
        if (source != null && entity instanceof Player owner) {
            for (ITrinket trinket : activeTrinkets(owner)) {
                if (trinket.onAttacked(owner, source))
                    return true;
            }
        }
        return false;
    }

    public static void onHurt(LivingEntity entity, DamageSource source, float amount) {
        if (source == null)
            return;
        if (source.getEntity() instanceof Player attacker) {
            activeTrinkets(attacker).forEach(trinket -> trinket.onAttack(attacker, entity, source, amount));
        }
        if (entity instanceof Player owner) {
            activeTrinkets(owner).forEach(trinket -> trinket.onHurt(owner, source, amount));
        }
    }

    public static float modifyAttackDamage(Player attacker, Entity target, DamageSource source, float amount) {
        float damage = amount;
        for (ITrinket trinket : activeTrinkets(attacker)) {
            damage = trinket.modifyAttackDamage(attacker, target, source, damage);
        }
        return damage;
    }

    public static boolean onDeath(LivingEntity entity, DamageSource source) {
        if (source == null)
            return false;
        if (entity instanceof Player owner) {
            for (ITrinket trinket : activeTrinkets(owner)) {
                if (trinket.preventDeath(owner, source))
                    return true;
            }
        }
        if (source.getEntity() instanceof Player killer) {
            activeTrinkets(killer).forEach(trinket -> trinket.onKill(killer, entity, source));
            UnlockHandler.kill(killer, entity instanceof EnderDragon || entity instanceof WitherBoss);
        }
        return false;
    }

    public static List<ItemStack> getExtraDrops(Player player, LivingEntity target) {
        List<ItemStack> drops = new ArrayList<>();
        activeTrinkets(player).forEach(trinket -> trinket.addKillDrops(player, target, drops));
        return drops;
    }

    public static boolean shouldDenyMobEffect(Player player, Holder<MobEffect> effect) {
        return activeTrinkets(player).stream().anyMatch(trinket -> trinket.preventEffect(player, effect));
    }

    public static int getLootingBonus(Player player) {
        return activeTrinkets(player).stream().mapToInt(trinket -> trinket.getLootingBonus(player)).sum();
    }

    public static void onItemEaten(Player player, ItemStack stack) {
        activeTrinkets(player).forEach(trinket -> trinket.onItemEaten(player, stack));
    }

    public static float onBreakSpeed(Player player, BlockState state, float speed) {
        float result = speed;
        for (ITrinket trinket : activeTrinkets(player)) {
            result = trinket.modifyBreakSpeed(player, state, result);
        }
        return result;
    }

    public static void onBreak(Player player, BlockPos pos, BlockState state) {
        activeTrinkets(player).forEach(trinket -> trinket.onBlockBreak(player, pos, state));
        UnlockHandler.checkBlockHarvest(player, player.level(), pos, state);
    }

    /// The player's active trinkets, in registration order.
    private static List<ITrinket> activeTrinkets(Player player) {
        List<ITrinket> trinkets = new ArrayList<>(LostTrinketsAPI.getTrinkets(player).getActiveTrinkets());
        trinkets.sort(Comparator.comparingInt(trinket -> BuiltInRegistries.ITEM.getId(trinket.getItem())));
        return trinkets;
    }
}
