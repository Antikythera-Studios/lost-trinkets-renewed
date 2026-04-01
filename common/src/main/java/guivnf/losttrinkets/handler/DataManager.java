package guivnf.losttrinkets.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.config.Configs;
import guivnf.losttrinkets.network.packet.SyncDataPacket;

public class DataManager {

    public static void clone(Player oldPlayer, Player newPlayer, boolean wasDeath) {
        PlayerData oldData = LostTrinketsAPI.getData(oldPlayer);
        PlayerData newData = LostTrinketsAPI.getData(newPlayer);
        newData.deserializeNBT(oldData.serializeNBT());

        Trinkets trinkets = LostTrinketsAPI.getTrinkets(newPlayer);
        trinkets.getActiveTrinkets().forEach(trinket -> {
            if (trinket instanceof Trinket) {
                ((Trinket<?>) trinket).applyAttributes(newPlayer);
            }
        });

        if (!wasDeath) {
            newPlayer.setHealth(oldPlayer.getHealth());
        }

        sync(newPlayer);
        newData.setSync(true);
    }

    public static void update(ServerPlayer player) {
        PlayerData data = LostTrinketsAPI.getData(player);
        if (data.isSync()) {
            LostTrinkets.NET.toTrackingAndSelf(new SyncDataPacket(player), player);
            data.setSync(false);
        }
    }

    public static void loggedIn(Player player) {
        PlayerData data = LostTrinketsAPI.getData(player);

        data.allowFlying = false;
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        trinkets.initSlots(Configs.GENERAL.startSlots);
        trinkets.removeDisabled(player);
        sync(player);
    }

    public static void loggedOut(Player player) {
        PlayerData data = LostTrinketsAPI.getData(player);
        data.wasFlying = player.getAbilities().flying;

        data.allowFlying = false;
    }

    public static void respawn(Player player) {
        sync(player);
    }

    public static void changedDimension(Player player) {
        sync(player);
    }

    public static void trackPlayer(ServerPlayer trackedPlayer, Player tracker) {
        LostTrinkets.NET.toClient(new SyncDataPacket(trackedPlayer), tracker);
    }

    static void sync(Player player) {
        if (player instanceof ServerPlayer) {
            LostTrinkets.NET.toClient(new SyncDataPacket(player), player);
        }
    }
}
