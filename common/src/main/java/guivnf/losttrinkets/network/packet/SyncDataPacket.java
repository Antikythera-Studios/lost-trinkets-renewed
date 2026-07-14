package guivnf.losttrinkets.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.network.LTPacket;

import java.util.Objects;
import java.util.UUID;

public class SyncDataPacket implements LTPacket {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, "sync_data");

    private final UUID uuid;
    private final CompoundTag nbt;

    public SyncDataPacket(UUID uuid, CompoundTag nbt) {
        this.uuid = uuid;
        this.nbt = nbt;
    }

    public SyncDataPacket(Player player) {
        this(player.getUUID(), LostTrinketsAPI.getData(player).serializeNBT());
    }

    public UUID getUuid() { return uuid; }
    public CompoundTag getNbt() { return nbt; }

    @Override
    public Identifier getId() {
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

    public static void register(java.util.function.BiConsumer<SyncDataPacket, NetworkManager.PacketContext> handler) {
        LostTrinkets.NET.registerS2C(ID, SyncDataPacket::decode, handler);
    }
}
