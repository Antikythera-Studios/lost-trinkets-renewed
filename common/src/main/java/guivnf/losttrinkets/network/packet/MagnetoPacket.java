package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.network.LTPacket;

public class MagnetoPacket implements LTPacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(LostTrinkets.MOD_ID, "magneto");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
    }

    public static MagnetoPacket decode(RegistryFriendlyByteBuf buf) {
        return new MagnetoPacket();
    }

    public static void handle(MagnetoPacket msg, NetworkManager.PacketContext ctx) {
        Player player = ctx.getPlayer();
        if (player != null) {
            guivnf.losttrinkets.item.trinkets.MagnetoTrinket.tryCollectServer(player);
        }
    }

    public static void register() {
        LostTrinkets.NET.registerC2S(ID, MagnetoPacket::decode, MagnetoPacket::handle);
    }
}
