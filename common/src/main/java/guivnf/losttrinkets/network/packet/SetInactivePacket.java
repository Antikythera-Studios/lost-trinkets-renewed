package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.network.LTPacket;

import java.util.List;

public class SetInactivePacket implements LTPacket {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, "set_inactive");

    private final int trinket;

    public SetInactivePacket(int trinket) {
        this.trinket = trinket;
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(trinket);
    }

    public static SetInactivePacket decode(RegistryFriendlyByteBuf buf) {
        return new SetInactivePacket(buf.readInt());
    }

    public static void handle(SetInactivePacket msg, NetworkManager.PacketContext ctx) {
        Player player = ctx.getPlayer();
        if (player != null) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            List<ITrinket> items = trinkets.getActiveTrinkets();
            if (msg.trinket >= 0 && msg.trinket < items.size()) {
                trinkets.setInactive(items.get(msg.trinket), player);
            }
        }
    }

    public static void register() {
        LostTrinkets.NET.registerC2S(ID, SetInactivePacket::decode, SetInactivePacket::handle);
    }
}
