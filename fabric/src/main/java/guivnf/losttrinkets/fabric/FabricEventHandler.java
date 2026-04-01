package guivnf.losttrinkets.fabric;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.level.ServerPlayer;
import guivnf.losttrinkets.command.MainCommand;
import guivnf.losttrinkets.handler.DataManager;
import guivnf.losttrinkets.handler.EventHandler;
import guivnf.losttrinkets.util.ServerHelper;

public class FabricEventHandler {

    public static void register() {
        TickEvent.PLAYER_POST.register(player -> {
            if (!player.level().isClientSide) {
                EventHandler.tick(player);
                if (player instanceof ServerPlayer sp) {
                    DataManager.update(sp);
                }
            }
        });

        EntityEvent.ADD.register((entity, level) -> {
            if (!level.isClientSide() && EventHandler.joinWorld(entity)) {
                return EventResult.interruptFalse();
            }
            return EventResult.pass();
        });

        EntityEvent.LIVING_HURT.register((entity, source, amount) -> {
            if (EventHandler.onAttack(entity, source)) {
                return EventResult.interruptFalse();
            }
            EventHandler.onHurt(entity, source, amount);
            return EventResult.pass();
        });

        EntityEvent.LIVING_DEATH.register((entity, source) -> {
            if (EventHandler.onDeath(entity, source)) {
                return EventResult.interruptFalse();
            }
            return EventResult.pass();
        });

        ExplosionEvent.PRE.register((level, explosion) -> {
            if (EventHandler.onExplosionStart(explosion.getIndirectSourceEntity())) {
                return EventResult.interruptFalse();
            }
            return EventResult.pass();
        });

        PlayerEvent.PLAYER_CLONE
                .register((oldPlayer, newPlayer, wonGame) -> DataManager.clone(oldPlayer, newPlayer, !wonGame));

        PlayerEvent.PLAYER_JOIN.register(DataManager::loggedIn);

        PlayerEvent.PLAYER_QUIT.register(DataManager::loggedOut);

        PlayerEvent.PLAYER_RESPAWN.register((player, conqueredEnd, removalReason) -> DataManager.respawn(player));

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD
                .register((player, origin, destination) -> DataManager.changedDimension(player));

        BlockEvent.BREAK.register((level, pos, state, player, xp) -> {
            if (!level.isClientSide() && player != null) {
                EventHandler.onBreak(player, pos, state);
            }
            return EventResult.pass();
        });

        CommandRegistrationCallback.EVENT
                .register((dispatcher, registryAccess, environment) -> MainCommand.register(dispatcher));

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerHelper.setServer(server);
            guivnf.losttrinkets.config.Configs.GENERAL.refresh();
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> ServerHelper.setServer(null));
    }
}
