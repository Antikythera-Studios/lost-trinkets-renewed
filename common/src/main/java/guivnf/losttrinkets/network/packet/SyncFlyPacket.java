package guivnf.losttrinkets.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.network.LTPacket;

public class SyncFlyPacket implements LTPacket {
    public static final ResourceLocation ID = new ResourceLocation(LostTrinkets.MOD_ID, "sync_fly");

    public final boolean fly;

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
}
