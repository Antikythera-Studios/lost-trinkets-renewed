package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

import java.util.Collections;
import java.util.Optional;

public class WarmVoidTrinket extends Trinket<WarmVoidTrinket> implements ITickableTrinket {
    public WarmVoidTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(Level level, BlockPos pos, Player player) {
        if (player instanceof ServerPlayer serverPlayer
                && player.getY() + Math.min(0, player.getDeltaMovement().y) <= level.getMinBuildHeight()) {
            if (!player.isPassenger() && !player.isVehicle()) {
                teleportToSpawnPoint(serverPlayer);
            }
        }
    }

    private static void teleportToSpawnPoint(ServerPlayer player) {
        player.stopRiding();
        player.setDeltaMovement(Vec3.ZERO);
        player.fallDistance = 0;

        MinecraftServer server = player.getServer();
        if (server == null)
            return;

        BlockPos spawnPos = player.getRespawnPosition();
        float spawnAngle = player.getRespawnAngle();
        boolean spawnForced = player.isRespawnForced();
        ResourceKey<Level> spawnDimension = player.getRespawnDimension();

        ServerLevel spawnWorld = server.getLevel(spawnDimension);
        Optional<Vec3> respawnPos = Optional.empty();
        if (spawnWorld != null && spawnPos != null) {
            respawnPos = ServerPlayer.findRespawnPositionAndUseSpawnBlock(spawnWorld, spawnPos, spawnAngle, spawnForced,
                    true);
        }

        if (spawnWorld == null) {
            spawnWorld = server.overworld();
        }

        ServerLevel destWorld = spawnWorld;
        if (respawnPos.isPresent()) {
            Vec3 dest = respawnPos.get();
            if (player.level() == destWorld) {
                player.connection.teleport(dest.x, dest.y, dest.z, spawnAngle, 0);
            } else {
                player.teleportTo(destWorld, dest.x, dest.y, dest.z, Collections.emptySet(), spawnAngle, 0);
            }
        } else {
            if (spawnPos != null) {
                player.connection.send(
                        new ClientboundGameEventPacket(ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE, 0.0F));
            }
            BlockPos worldSpawn = destWorld.getSharedSpawnPos();
            double sx = worldSpawn.getX() + 0.5;
            double sy = worldSpawn.getY();
            double sz = worldSpawn.getZ() + 0.5;
            if (player.level() == destWorld) {
                player.connection.teleport(sx, sy, sz, destWorld.getSharedSpawnAngle(), 0);
            } else {
                player.teleportTo(destWorld, sx, sy, sz, Collections.emptySet(), destWorld.getSharedSpawnAngle(), 0);
            }
        }

        // shift up if inside a block
        while (!destWorld.noCollision(player) && player.getY() < destWorld.getMaxBuildHeight()) {
            player.setPos(player.getX(), player.getY() + 1.0, player.getZ());
        }
    }
}
