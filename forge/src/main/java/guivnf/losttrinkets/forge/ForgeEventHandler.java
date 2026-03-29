package guivnf.losttrinkets.forge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.TickEvent;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.command.MainCommand;
import guivnf.losttrinkets.entity.DarkVexEntity;
import guivnf.losttrinkets.entity.Entities;
import guivnf.losttrinkets.handler.DataManager;
import guivnf.losttrinkets.handler.EventHandler;
import guivnf.losttrinkets.util.ServerHelper;

import java.util.List;

@Mod.EventBusSubscriber(modid = LostTrinkets.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            EventHandler.tick(event.player);
            if (event.player instanceof net.minecraft.server.level.ServerPlayer sp) {
                DataManager.update(sp);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        EventHandler.onLivingUpdate(event.getEntity());
    }

    @SubscribeEvent
    public static void onExplosionStart(ExplosionEvent.Start event) {
        Entity exploder = event.getExplosion().getIndirectSourceEntity();
        if (EventHandler.onExplosionStart(exploder)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (EventHandler.joinWorld(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (EventHandler.onAttack(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        float amount = EventHandler.onHurt(event.getEntity(), event.getSource(), event.getAmount());
        event.setAmount(amount);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (EventHandler.onDeath(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity target = event.getEntity();
        Entity killer = event.getSource().getEntity();
        if (killer instanceof Player player) {
            List<ItemStack> extras = EventHandler.getExtraDrops(player, target);
            extras.forEach(stack -> event.getDrops().add(new net.minecraft.world.entity.item.ItemEntity(
                    target.level(), target.getX(), target.getY(), target.getZ(), stack)));
        }
    }

    @SubscribeEvent
    public static void onPotion(MobEffectEvent.Applicable event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (EventHandler.shouldDenyMobEffect(player, event.getEffectInstance().getEffect())) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        EventHandler.onCriticalHit(event.getEntity(), event.getTarget());
    }

    @SubscribeEvent
    public static void onLooting(LootingLevelEvent event) {
        int bonus = EventHandler.onLooting(event.getDamageSource());
        event.setLootingLevel(event.getLootingLevel() + bonus);
    }

    @SubscribeEvent
    public static void onRightClickAir(PlayerInteractEvent.RightClickEmpty event) {
        EventHandler.onRightClickAir(event.getEntity(), event.getHand());
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        float newSpeed = EventHandler.onBreakSpeed(event.getEntity(), event.getOriginalSpeed());
        if (newSpeed != event.getOriginalSpeed()) {
            event.setNewSpeed(newSpeed);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() != null && !event.getLevel().isClientSide()) {
            EventHandler.onBreak(event.getPlayer(),
                    event.getPos(),
                    event.getState());
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        MainCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        DataManager.clone(event.getOriginal(), event.getEntity(), event.isWasDeath());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        DataManager.loggedIn(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        DataManager.loggedOut(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        DataManager.respawn(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        DataManager.changedDimension(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        Entity tracked = event.getTarget();
        if (tracked instanceof net.minecraft.server.level.ServerPlayer trackedPlayer) {
            DataManager.trackPlayer(trackedPlayer, event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ServerHelper.setServer(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ServerHelper.setServer(null);
    }

    // called from mod event bus
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(Entities.DARK_VEX.get(), DarkVexEntity.createAttributes().build());
    }
}
