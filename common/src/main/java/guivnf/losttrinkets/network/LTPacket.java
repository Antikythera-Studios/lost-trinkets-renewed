package guivnf.losttrinkets.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface LTPacket {
    ResourceLocation getId();

    void write(RegistryFriendlyByteBuf buf);
}
