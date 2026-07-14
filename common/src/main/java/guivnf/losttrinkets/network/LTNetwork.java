package guivnf.losttrinkets.network;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class LTNetwork {

    private final String modId;

    public LTNetwork(String modId) {
        this.modId = modId;
    }

    public Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(modId, path);
    }

    public <T> void registerS2C(Identifier id,
            Function<RegistryFriendlyByteBuf, T> decoder,
            BiConsumer<T, NetworkManager.PacketContext> handler) {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, id, (buf, context) -> {
            T packet = decoder.apply(buf);
            context.queue(() -> handler.accept(packet, context));
        });
    }

    public <T> void registerC2S(Identifier id,
            Function<RegistryFriendlyByteBuf, T> decoder,
            BiConsumer<T, NetworkManager.PacketContext> handler) {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, id, (buf, context) -> {
            T packet = decoder.apply(buf);
            context.queue(() -> handler.accept(packet, context));
        });
    }

    private static RegistryFriendlyByteBuf makeBuf(Consumer<RegistryFriendlyByteBuf> writer) {
        RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), null);
        writer.accept(buf);
        return buf;
    }

    public void toServer(Identifier id, Consumer<RegistryFriendlyByteBuf> writer) {
        if (!NetworkManager.canServerReceive(id)) return;
        NetworkManager.sendToServer(id, makeBuf(writer));
    }

    public void toServer(LTPacket packet) {
        if (!NetworkManager.canServerReceive(packet.getId())) return;
        NetworkManager.sendToServer(packet.getId(), makeBuf(packet::write));
    }

    public void toClient(Identifier id, Consumer<RegistryFriendlyByteBuf> writer, Player player) {
        if (player instanceof ServerPlayer sp) {
            NetworkManager.sendToPlayer(sp, id, makeBuf(writer));
        }
    }

    public void toClient(LTPacket packet, Player player) {
        if (player instanceof ServerPlayer sp) {
            NetworkManager.sendToPlayer(sp, packet.getId(), makeBuf(packet::write));
        }
    }

    public void toTrackingAndSelf(LTPacket packet, ServerPlayer player) {
        if (player.level().getServer() == null)
            return;
        ServerLevel level = (ServerLevel) player.level();
        level.getChunkSource().chunkMap.getPlayers(player.chunkPosition(), false)
                .forEach(p -> NetworkManager.sendToPlayer(p, packet.getId(), makeBuf(packet::write)));
        NetworkManager.sendToPlayer(player, packet.getId(), makeBuf(packet::write));
    }
}
