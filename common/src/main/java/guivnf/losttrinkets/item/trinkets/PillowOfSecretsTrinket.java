package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class PillowOfSecretsTrinket extends Trinket<PillowOfSecretsTrinket> implements ITickableTrinket {
    public PillowOfSecretsTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(Level level, BlockPos pos, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            int timeSinceRest = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
            int j = Mth.clamp(timeSinceRest, 1, Integer.MAX_VALUE);
            if (j > 12000) {
                serverPlayer.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
            }
        }
    }
}
