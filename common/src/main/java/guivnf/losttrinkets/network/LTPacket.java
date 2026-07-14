package guivnf.losttrinkets.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;

public interface LTPacket {
    Identifier getId();

    void write(RegistryFriendlyByteBuf buf);
}
