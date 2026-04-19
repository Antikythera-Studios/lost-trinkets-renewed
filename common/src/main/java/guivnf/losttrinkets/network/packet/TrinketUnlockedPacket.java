package guivnf.losttrinkets.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.network.LTPacket;

public class TrinketUnlockedPacket implements LTPacket {
    public static final ResourceLocation ID = new ResourceLocation(LostTrinkets.MOD_ID, "trinket_unlocked");

    public final String key;

    public TrinketUnlockedPacket(String key) {
        this.key = key;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(key);
    }

    public static TrinketUnlockedPacket decode(FriendlyByteBuf buf) {
        return new TrinketUnlockedPacket(buf.readUtf(32767));
    }
}
