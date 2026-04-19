package guivnf.losttrinkets.client.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import guivnf.losttrinkets.LostTrinkets;
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

public class ClientPackets {

    public static void register() {
        LostTrinkets.NET.registerS2C(SyncDataPacket.ID, SyncDataPacket::decode, ClientPackets::handleSyncData);
        LostTrinkets.NET.registerS2C(SyncFlyPacket.ID, SyncFlyPacket::decode, ClientPackets::handleSyncFly);
        LostTrinkets.NET.registerS2C(TrinketUnlockedPacket.ID, TrinketUnlockedPacket::decode, ClientPackets::handleTrinketUnlocked);
    }

    private static void handleSyncData(SyncDataPacket msg, NetworkManager.PacketContext ctx) {
        Minecraft mc = Minecraft.getInstance();
        // prefer mc.player for local UUID — world.getPlayerByUUID can miss the local
        // player right after respawn/dimension-change before the entity is tracked.
        if (mc.player != null && mc.player.getUUID().equals(msg.uuid)) {
            LostTrinketsAPI.getData(mc.player).deserializeNBT(msg.nbt);
            Screens.checkScreenRefresh();
            return;
        }
        // update other nearby players (so trinket effects render correctly for them)
        MC.world().ifPresent(world -> {
            Player player = world.getPlayerByUUID(msg.uuid);
            if (player != null) {
                LostTrinketsAPI.getData(player).deserializeNBT(msg.nbt);
            }
        });
    }

    private static void handleSyncFly(SyncFlyPacket msg, NetworkManager.PacketContext ctx) {
        MC.player().ifPresent(player -> {
            PlayerData data = LostTrinketsAPI.getData(player);
            data.allowFlying = msg.fly;
            player.getAbilities().mayfly = msg.fly;
            if (!msg.fly) {
                player.getAbilities().flying = false;
            }
        });
    }

    private static void handleTrinketUnlocked(TrinketUnlockedPacket msg, NetworkManager.PacketContext ctx) {
        MC.player().ifPresent(player -> {
            Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(msg.key));
            if (item instanceof ITrinket) {
                HudHandler.add(new Toast((ITrinket) item));
                player.playSound(Sounds.UNLOCK.get(), 1.0F, 1.0F);
            }
        });
    }
}
