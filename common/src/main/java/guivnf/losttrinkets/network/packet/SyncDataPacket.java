package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.client.screen.Screens;
import guivnf.losttrinkets.client.util.MC;
import guivnf.losttrinkets.network.LTPacket;

import java.util.Objects;
import java.util.UUID;

public class SyncDataPacket implements LTPacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(LostTrinkets.MOD_ID, "sync_data");

    private final UUID uuid;
    private final CompoundTag nbt;

    public SyncDataPacket(UUID uuid, CompoundTag nbt) {
        this.uuid = uuid;
        this.nbt = nbt;
    }

    public SyncDataPacket(Player player) {
        this(player.getUUID(), LostTrinketsAPI.getData(player).serializeNBT());
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeNbt(nbt);
    }

    public static SyncDataPacket decode(RegistryFriendlyByteBuf buf) {
        return new SyncDataPacket(buf.readUUID(), Objects.requireNonNull(buf.readNbt()));
    }

    public static void handle(SyncDataPacket msg, NetworkManager.PacketContext ctx) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null && mc.player.getUUID().equals(msg.uuid)) {
            LostTrinketsAPI.getData(mc.player).deserializeNBT(msg.nbt);
            Screens.checkScreenRefresh();
            return;
        }

        MC.world().ifPresent(world -> {
            Player player = world.getPlayerByUUID(msg.uuid);
            if (player != null) {
                LostTrinketsAPI.getData(player).deserializeNBT(msg.nbt);
            }
        });
    }

    public static void register() {
        LostTrinkets.NET.registerS2C(ID, SyncDataPacket::decode, SyncDataPacket::handle);
    }
}
