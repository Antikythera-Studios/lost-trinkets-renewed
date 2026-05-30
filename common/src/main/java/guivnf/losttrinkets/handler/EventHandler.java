package guivnf.losttrinkets.handler;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.item.trinkets.*;

import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Collections;
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
        IceShardTrinket.frostWalk(player, player.blockPosition());
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
        FireMindTrinket.onLivingUpdate(entity);
        TargetHandler.onLivingUpdate(entity);
    }

    public static boolean onExplosionStart(Entity exploder) {
        if (exploder instanceof Creeper) {
            Creeper creeper = (Creeper) exploder;
            LivingEntity target = creeper.getTarget();
            if (target instanceof Player) {
                Player player = (Player) target;
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                if (trinkets.isActive(Itms.CREEPO.get())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean joinWorld(Entity entity) {
        if (OctopickTrinket.collectDrops(entity))
            return true;
        BigFootTrinket.addAvoidGoal(entity);
        return false;
    }

    public static boolean onAttack(LivingEntity entityLiving, net.minecraft.world.damagesource.DamageSource source) {
        if (source == null)
            return false;
        if (BlazeHeartTrinket.isImmuneToFire(entityLiving, source)) {
            return true;
        }
        if (MadAuraTrinket.shouldCancelAttack(entityLiving, source))
            return true;
        OctopusLegTrinket.onAttack(entityLiving, source);
        return false;
    }

    public static float onHurt(LivingEntity entityLiving, net.minecraft.world.damagesource.DamageSource source,
            float amount) {
        if (source == null)
            return amount;
        if (entityLiving instanceof Player player) {
            RubyHeartTrinket.saveHealth(player);
        }
        amount = DarkDaggerTrinket.onHurt(entityLiving, source, amount);
        amount = DarkEggTrinket.onHurt(entityLiving, source, amount);
        amount = DropSpindleTrinket.onHurt(entityLiving, source, amount);
        amount = EmberTrinket.onHurt(entityLiving, source, amount);
        amount = GoldenSwatterTrinket.onHurt(entityLiving, source, amount);
        amount = MadPiggyTrinket.onHurt(entityLiving, source, amount);
        amount = MirrorTrinket.onHurt(entityLiving, source, amount);
        amount = SerpentToothTrinket.onHurt(entityLiving, source, amount);
        amount = StarfishTrinket.onHurt(entityLiving, source, amount);
        amount = SlingshotTrinket.onHurt(entityLiving, source, amount);
        amount = WitherHandTrinket.onHurt(entityLiving, source, amount);

        if (source.getEntity() instanceof Player) {
            Player player = (Player) source.getEntity();
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.SILVER_NAIL.get())) {
                amount *= 1.1F;
            }
            if (trinkets.isActive(Itms.GLORY_SHARDS.get())) {
                amount *= 1.2F;
            }
        }
        return amount;
    }

    public static boolean onDeath(LivingEntity entityLiving, net.minecraft.world.damagesource.DamageSource source) {
        if (source == null)
            return false;
        boolean cancelled = RubyHeartTrinket.onDeath(entityLiving, source);
        if (!cancelled) {
            Entity killer = source.getEntity();
            if (killer instanceof Player player) {
                boolean isBoss = entityLiving instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon
                        || entityLiving instanceof net.minecraft.world.entity.boss.wither.WitherBoss;
                UnlockHandler.kill(player, isBoss);
            }
        }
        return cancelled;
    }

    public static List<ItemStack> getExtraDrops(Player player, LivingEntity target) {
        List<ItemStack> extras = new ArrayList<>();
        ItemStack butchersExtra = ButchersCleaverTrinket.getExtraDrop(player, target);
        if (!butchersExtra.isEmpty())
            extras.add(butchersExtra);
        ItemStack skullExtra = GoldenSkullTrinket.getExtraDrop(player, target);
        if (!skullExtra.isEmpty())
            extras.add(skullExtra);
        extras.addAll(TreasureRingTrinket.getExtraDrops(player, target));
        return extras;
    }

    public static boolean shouldDenyMobEffect(Player player, Holder<MobEffect> effect) {
        return CoffeeBeanTrinket.shouldDenyEffect(player, effect)
                || MagicalHerbsTrinket.shouldDenyEffect(player, effect)
                || OxalisTrinket.shouldDenyEffect(player, effect)
                || TeaLeafTrinket.shouldDenyEffect(player, effect);
    }

    public static void onCriticalHit(Player player, Entity target) {
        CreepoTrinket.onCriticalHit(player, target);
    }

    public static void onItemEaten(Player player, ItemStack stack, Level level) {
        GoldenMelonTrinket.onItemEaten(player, stack, level);
        LunchBagTrinket.onItemEaten(player, stack, level);
    }

    public static float onBreakSpeed(Player player, float original) {
        return MinersPickTrinket.onBreakSpeed(player, original);
    }

    public static void onBreak(Player player, net.minecraft.core.BlockPos pos,
            net.minecraft.world.level.block.state.BlockState state) {
        OctopickTrinket.onBreak(player, pos, state);
        UnlockHandler.checkBlockHarvest(player, player.level(), pos, state);
    }
}
