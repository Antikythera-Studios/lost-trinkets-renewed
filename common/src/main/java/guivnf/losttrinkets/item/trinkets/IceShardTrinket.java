package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class IceShardTrinket extends Trinket<IceShardTrinket> implements ITickableTrinket {
    private static final int RADIUS = 2; // frost walker I

    public IceShardTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(Level level, BlockPos pos, Player player) {
        for (BlockPos waterPos : BlockPos.betweenClosed(pos.offset(-RADIUS, -1, -RADIUS), pos.offset(RADIUS, -1, RADIUS))) {
            if (level.getBlockState(waterPos).is(Blocks.WATER) && level.isEmptyBlock(waterPos.above())) {
                level.setBlockAndUpdate(waterPos, Blocks.FROSTED_ICE.defaultBlockState());
            }
        }
    }
}
