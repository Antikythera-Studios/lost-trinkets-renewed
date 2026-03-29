package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.network.LTPacket;

import java.util.List;

public class SetActivePacket implements LTPacket {
    public static final ResourceLocation ID = new ResourceLocation(LostTrinkets.MOD_ID, "set_active");

    private final int trinket;

    public SetActivePacket(int trinket) {
        this.trinket = trinket;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(trinket);
    }

    public static SetActivePacket decode(FriendlyByteBuf buf) {
        return new SetActivePacket(buf.readInt());
    }

    public static void handle(SetActivePacket msg, NetworkManager.PacketContext ctx) {
        Player player = ctx.getPlayer();
        if (player != null) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            List<ITrinket> items = trinkets.getAvailableTrinkets();
            if (msg.trinket >= 0 && msg.trinket < items.size()) {
                trinkets.setActive(items.get(msg.trinket), player);
            }
        }
    }

    public static void register() {
        LostTrinkets.NET.registerC2S(ID, SetActivePacket::decode, SetActivePacket::handle);
    }
}
