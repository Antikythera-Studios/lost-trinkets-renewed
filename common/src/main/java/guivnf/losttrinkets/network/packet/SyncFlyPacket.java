package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.network.LTPacket;

import java.util.function.BiConsumer;

public class SyncFlyPacket implements LTPacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(LostTrinkets.MOD_ID, "sync_fly");

    private final boolean fly;

    public SyncFlyPacket(boolean fly) {
        this.fly = fly;
    }

    public boolean isFly() { return fly; }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(fly);
    }

    public static SyncFlyPacket decode(RegistryFriendlyByteBuf buf) {
        return new SyncFlyPacket(buf.readBoolean());
    }

    public static void register(BiConsumer<SyncFlyPacket, NetworkManager.PacketContext> handler) {
        LostTrinkets.NET.registerS2C(ID, SyncFlyPacket::decode, handler);
    }
}
