package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class GoldenRingTrinket extends Trinket<GoldenRingTrinket> implements ITickableTrinket {
    public GoldenRingTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(Level level, BlockPos pos, Player player) {

        if (level.getGameTime() % 40 == 0 && !player.swinging) {
            for (InteractionHand hand : InteractionHand.values()) {
                ItemStack stack = player.getItemInHand(hand);
                if (!stack.isEmpty() && stack.isDamaged()) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                    break;
                }
            }
        }
    }
}
