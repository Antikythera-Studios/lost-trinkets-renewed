package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.Itms;

public class ThaCloudTrinket extends Trinket<ThaCloudTrinket> implements ITickableTrinket {
    public ThaCloudTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(Level level, BlockPos pos, Player player) {
        if (player.fallDistance > 3.0F) {
            player.fallDistance = 0.0F;
        }
    }

    public static void clientTick(Player player) {
        if (!LostTrinketsAPI.getTrinkets(player).isActive(Itms.THA_CLOUD.get()))
            return;
        if (player.fallDistance > 3.0F && player.getDeltaMovement().y < 0
                && !player.level().isEmptyBlock(player.blockPosition().below(3))) {
            Vec3 motion = player.getDeltaMovement();
            // Give a slight upward push not stop them entirely.
            player.setDeltaMovement(motion.x, 0.4D, motion.z);

            player.fallDistance = 0.0F;
            for (int i = 0; i < 8; i++) {
                double angle = 2 * Math.PI * i / 8.0D;
                double px = player.getX() + 0.3D * Math.cos(angle);
                double pz = player.getZ() + 0.3D * Math.sin(angle);
                player.level().addParticle(ParticleTypes.CLOUD, px, player.getY(), pz, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
