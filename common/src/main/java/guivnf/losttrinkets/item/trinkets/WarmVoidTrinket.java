package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
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
                && player.getY() + Math.min(0, player.getDeltaMovement().y) <= level.getMinY()) {
            if (!player.isPassenger() && !player.isVehicle()) {
                teleportToSpawnPoint(serverPlayer);
            }
        }
    }

    private static void teleportToSpawnPoint(ServerPlayer player) {
        player.stopRiding();
        player.setDeltaMovement(Vec3.ZERO);
        player.fallDistance = 0;

        TeleportTransition transition = player.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING);
        var destWorld = transition.newLevel();
        Vec3 dest = transition.position();
        float yRot = transition.yRot();

        if (transition.missingRespawnBlock()) {
            if (player.getRespawnConfig() != null) {
                player.connection.send(
                        new ClientboundGameEventPacket(ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE, 0.0F));
            }
            net.minecraft.world.level.storage.LevelData.RespawnData rd = destWorld.getRespawnData();
            BlockPos worldSpawn = rd.globalPos().pos();
            dest = new Vec3(worldSpawn.getX() + 0.5, worldSpawn.getY(), worldSpawn.getZ() + 0.5);
            yRot = rd.yaw();
        }

        if (player.level() == destWorld) {
            player.connection.teleport(dest.x, dest.y, dest.z, yRot, 0);
        } else {
            player.teleportTo(destWorld, dest.x, dest.y, dest.z, Collections.emptySet(), yRot, 0, false);
        }

        while (!destWorld.noCollision(player) && player.getY() < destWorld.getMaxY()) {
            player.setPos(player.getX(), player.getY() + 1.0, player.getZ());
        }
    }
}
