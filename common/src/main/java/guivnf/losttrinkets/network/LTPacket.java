package guivnf.losttrinkets.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface LTPacket {
    ResourceLocation getId();

    void write(FriendlyByteBuf buf);
}
