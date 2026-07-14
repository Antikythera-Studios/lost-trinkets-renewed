package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class GoldenBeltTrinket extends Trinket<GoldenBeltTrinket> implements ITickableTrinket {
    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };

    public GoldenBeltTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(Level level, BlockPos pos, Player player) {
        if (level.getGameTime() % 40 == 0) {
            for (EquipmentSlot slot : ARMOR_SLOTS) {
                ItemStack stack = player.getItemBySlot(slot);
                if (!stack.isEmpty() && stack.isDamaged()) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                    break;
                }
            }
        }
    }
}
