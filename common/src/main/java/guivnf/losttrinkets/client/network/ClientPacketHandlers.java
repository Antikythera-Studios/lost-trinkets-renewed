package guivnf.losttrinkets.client.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.client.Sounds;
import guivnf.losttrinkets.client.handler.hud.HudHandler;
import guivnf.losttrinkets.client.handler.hud.Toast;
import guivnf.losttrinkets.client.screen.Screens;
import guivnf.losttrinkets.client.util.MC;
import guivnf.losttrinkets.network.packet.SyncDataPacket;
import guivnf.losttrinkets.network.packet.SyncFlyPacket;
import guivnf.losttrinkets.network.packet.TrinketUnlockedPacket;

public class ClientPacketHandlers {

    public static void registerAll() {
        SyncDataPacket.register(ClientPacketHandlers::handleSyncData);
        TrinketUnlockedPacket.register(ClientPacketHandlers::handleTrinketUnlocked);
        SyncFlyPacket.register(ClientPacketHandlers::handleSyncFly);
    }

    public static void handleSyncData(SyncDataPacket msg, NetworkManager.PacketContext ctx) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null && mc.player.getUUID().equals(msg.getUuid())) {
            LostTrinketsAPI.getData(mc.player).deserializeNBT(msg.getNbt());
            Screens.checkScreenRefresh();
            return;
        }

        MC.world().ifPresent(world -> {
            Player player = world.getPlayerByUUID(msg.getUuid());
            if (player != null) {
                LostTrinketsAPI.getData(player).deserializeNBT(msg.getNbt());
            }
        });
    }

    public static void handleSyncFly(SyncFlyPacket msg, NetworkManager.PacketContext ctx) {
        MC.player().ifPresent(player -> {
            PlayerData data = LostTrinketsAPI.getData(player);
            data.allowFlying = msg.isFly();
            player.getAbilities().mayfly = msg.isFly();
            if (!msg.isFly()) {
                player.getAbilities().flying = false;
            }
        });
    }

    public static void handleTrinketUnlocked(TrinketUnlockedPacket msg, NetworkManager.PacketContext ctx) {
        MC.player().ifPresent(player -> {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(msg.getKey()));
            if (item instanceof ITrinket) {
                HudHandler.add(new Toast((ITrinket) item));
                player.playSound(Sounds.UNLOCK.get(), 1.0F, 1.0F);
            }
        });
    }
}
