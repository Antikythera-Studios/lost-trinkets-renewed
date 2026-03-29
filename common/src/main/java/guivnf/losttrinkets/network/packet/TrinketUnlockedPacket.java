package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.client.Sounds;
import guivnf.losttrinkets.client.handler.hud.HudHandler;
import guivnf.losttrinkets.client.handler.hud.Toast;
import guivnf.losttrinkets.client.util.MC;
import guivnf.losttrinkets.network.LTPacket;

public class TrinketUnlockedPacket implements LTPacket {
    public static final ResourceLocation ID = new ResourceLocation(LostTrinkets.MOD_ID, "trinket_unlocked");

    private final String key;

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

    public static void handle(TrinketUnlockedPacket msg, NetworkManager.PacketContext ctx) {
        MC.player().ifPresent(player -> {
            Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(msg.key));
            if (item instanceof ITrinket) {
                HudHandler.add(new Toast((ITrinket) item));
                player.playSound(Sounds.UNLOCK.get(), 1.0F, 1.0F);
            }
        });
    }

    public static void register() {
        LostTrinkets.NET.registerS2C(ID, TrinketUnlockedPacket::decode, TrinketUnlockedPacket::handle);
    }
}
