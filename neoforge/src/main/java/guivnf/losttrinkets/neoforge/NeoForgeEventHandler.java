package guivnf.losttrinkets.neoforge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.command.MainCommand;
import guivnf.losttrinkets.entity.DarkVexEntity;
import guivnf.losttrinkets.entity.Entities;
import guivnf.losttrinkets.handler.DataManager;
import guivnf.losttrinkets.handler.EventHandler;
import guivnf.losttrinkets.util.ServerHelper;

import java.util.List;

@EventBusSubscriber(modid = LostTrinkets.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoForgeEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            EventHandler.tick(player);
            if (player instanceof net.minecraft.server.level.ServerPlayer sp) {
                DataManager.update(sp);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity living) {
            EventHandler.onLivingUpdate(living);
        }
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
    public static void onLivingAttack(LivingIncomingDamageEvent event) {
        if (EventHandler.onAttack(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {
        float amount = EventHandler.onHurt(event.getEntity(), event.getSource(), event.getNewDamage());
        event.getContainer().setNewDamage(amount);
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
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        EventHandler.onCriticalHit(event.getEntity(), event.getTarget());
    }

    @SubscribeEvent
    public static void onRightClickAir(PlayerInteractEvent.RightClickEmpty event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()
                && event.getHand() == net.minecraft.world.InteractionHand.MAIN_HAND
                && player.getMainHandItem().isEmpty()) {
            guivnf.losttrinkets.item.trinkets.MagnetoTrinket.trySendCollect(player);
        }
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
            EventHandler.onBreak(event.getPlayer(), event.getPos(), event.getState());
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

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(Entities.DARK_VEX.get(), DarkVexEntity.createAttributes().build());
    }
}
