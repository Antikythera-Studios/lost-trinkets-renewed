package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.network.LTPacket;

import java.util.function.BiConsumer;

public class TrinketUnlockedPacket implements LTPacket {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, "trinket_unlocked");

    private final String key;

    public TrinketUnlockedPacket(String key) {
        this.key = key;
    }

    public String getKey() { return key; }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeUtf(key);
    }

    public static TrinketUnlockedPacket decode(RegistryFriendlyByteBuf buf) {
        return new TrinketUnlockedPacket(buf.readUtf(32767));
    }

    public static void register(BiConsumer<TrinketUnlockedPacket, NetworkManager.PacketContext> handler) {
        LostTrinkets.NET.registerS2C(ID, TrinketUnlockedPacket::decode, handler);
    }
}
