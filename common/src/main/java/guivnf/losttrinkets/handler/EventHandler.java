package guivnf.losttrinkets.handler;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.item.trinkets.*;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {

    public static void tick(Player player) {
        UnlockHandler.tick(player);
        PlayerData data = LostTrinketsAPI.getData(player);
        if (data.unlockDelay > 0) {
            data.unlockDelay--;
        }
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        IceShardTrinket.frostWalk(player, player.blockPosition());
        trinkets.getTickable().forEach(trinket -> trinket.tick(player.level(), player.blockPosition(), player));
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
