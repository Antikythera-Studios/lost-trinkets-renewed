package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class DropSpindleTrinket extends Trinket<DropSpindleTrinket> {
    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };

    public DropSpindleTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onHurt(LivingEntity entityLiving, DamageSource source, float amount) {
        Entity entity = source.getDirectEntity();
        if (entity instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.DROP_SPINDLE.get())) {
                for (EquipmentSlot slot : ARMOR_SLOTS) {
                    ItemStack stack = player.getItemBySlot(slot);
                    if (player.level().getRandom().nextInt(2) == 0) {
                        if (!stack.isEmpty() && stack.isDamaged()) {
                            stack.setDamageValue(stack.getDamageValue() - 1);
                        }
                    }
                }
            }
        }
        return amount;
    }
}
