package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.client.util.MC;
import guivnf.losttrinkets.network.LTPacket;

public class SyncFlyPacket implements LTPacket {
    public static final ResourceLocation ID = new ResourceLocation(LostTrinkets.MOD_ID, "sync_fly");

    private final boolean fly;

    public SyncFlyPacket(boolean fly) {
        this.fly = fly;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(fly);
    }

    public static SyncFlyPacket decode(FriendlyByteBuf buf) {
        return new SyncFlyPacket(buf.readBoolean());
    }

    public static void handle(SyncFlyPacket msg, NetworkManager.PacketContext ctx) {
        MC.player().ifPresent(player -> {
            PlayerData data = LostTrinketsAPI.getData(player);
            data.allowFlying = msg.fly;
            player.getAbilities().mayfly = msg.fly;
            if (!msg.fly) {
                player.getAbilities().flying = false;
            }
        });
    }

    public static void register() {
        LostTrinkets.NET.registerS2C(ID, SyncFlyPacket::decode, SyncFlyPacket::handle);
    }
}
