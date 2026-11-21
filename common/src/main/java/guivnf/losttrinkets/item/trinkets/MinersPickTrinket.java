package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class MinersPickTrinket extends Trinket<MinersPickTrinket> {
    public MinersPickTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public float modifyBreakSpeed(Player owner, BlockState state, float speed) {
        return speed + 3.7F;
    }
}
