package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.config.Configs;
import guivnf.losttrinkets.network.LTPacket;

public class UnlockSlotPacket implements LTPacket {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, "unlock_slot");

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
    }

    public static UnlockSlotPacket decode(RegistryFriendlyByteBuf buf) {
        return new UnlockSlotPacket();
    }

    public static void handle(UnlockSlotPacket msg, NetworkManager.PacketContext ctx) {
        Player player = ctx.getPlayer();
        if (player != null) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            int cost = Configs.GENERAL.calcCost(trinkets);
            if (cost >= 0) {
                if (player.isCreative()) {
                    trinkets.unlockSlot();
                } else if (player.experienceLevel >= cost) {
                    if (trinkets.unlockSlot()) {
                        player.giveExperienceLevels(-cost);
                    }
                }
            }

            LostTrinketsAPI.getData(player).setSync(true);
        }
    }

    public static void register() {
        LostTrinkets.NET.registerC2S(ID, UnlockSlotPacket::decode, UnlockSlotPacket::handle);
    }
}
