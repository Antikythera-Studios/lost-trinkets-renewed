package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.Itms;

public class IceShardTrinket extends Trinket<IceShardTrinket> {
    public IceShardTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void frostWalk(LivingEntity entity, BlockPos pos) {
        if (entity instanceof Player player) {
            if (!LostTrinketsAPI.getTrinkets(player).isActive(Itms.ICE_SHARD.get()))
                return;
            Level level = entity.level();
            if (level.isClientSide)
                return;
            // frost walk level 1 - freeze water within 2 blocks
            int radius = 2;
            for (BlockPos waterPos : BlockPos.betweenClosed(pos.offset(-radius, -1, -radius),
                    pos.offset(radius, -1, radius))) {
                if (level.getBlockState(waterPos).is(Blocks.WATER)) {
                    BlockPos above = waterPos.above();
                    if (level.isEmptyBlock(above)) {
                        level.setBlockAndUpdate(waterPos, Blocks.FROSTED_ICE.defaultBlockState());
                    }
                }
            }
        }
    }
}
