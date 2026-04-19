package guivnf.losttrinkets.network.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.network.LTPacket;

import java.util.Objects;
import java.util.UUID;

public class SyncDataPacket implements LTPacket {
    public static final ResourceLocation ID = new ResourceLocation(LostTrinkets.MOD_ID, "sync_data");

    public final UUID uuid;
    public final CompoundTag nbt;

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
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeNbt(nbt);
    }

    public static SyncDataPacket decode(FriendlyByteBuf buf) {
        return new SyncDataPacket(buf.readUUID(), Objects.requireNonNull(buf.readNbt()));
    }
}
