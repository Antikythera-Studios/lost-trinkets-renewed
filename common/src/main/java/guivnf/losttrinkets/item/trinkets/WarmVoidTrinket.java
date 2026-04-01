package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

import java.util.Collections;

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

        DimensionTransition transition = player.findRespawnPositionAndUseSpawnBlock(false, DimensionTransition.DO_NOTHING);
        var destWorld = transition.newLevel();
        Vec3 dest = transition.pos();
        float yRot = transition.yRot();

        if (transition.missingRespawnBlock()) {
            if (player.getRespawnPosition() != null) {
                player.connection.send(
                        new ClientboundGameEventPacket(ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE, 0.0F));
            }
            BlockPos worldSpawn = destWorld.getSharedSpawnPos();
            dest = new Vec3(worldSpawn.getX() + 0.5, worldSpawn.getY(), worldSpawn.getZ() + 0.5);
            yRot = destWorld.getSharedSpawnAngle();
        }

        if (player.level() == destWorld) {
            player.connection.teleport(dest.x, dest.y, dest.z, yRot, 0);
        } else {
            player.teleportTo(destWorld, dest.x, dest.y, dest.z, Collections.emptySet(), yRot, 0);
        }

        while (!destWorld.noCollision(player) && player.getY() < destWorld.getMaxBuildHeight()) {
            player.setPos(player.getX(), player.getY() + 1.0, player.getZ());
        }
    }
}
