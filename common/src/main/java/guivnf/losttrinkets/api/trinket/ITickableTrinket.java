package guivnf.losttrinkets.api.trinket;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ITickableTrinket extends ITrinket {
    void tick(Level level, BlockPos pos, Player player);
}
