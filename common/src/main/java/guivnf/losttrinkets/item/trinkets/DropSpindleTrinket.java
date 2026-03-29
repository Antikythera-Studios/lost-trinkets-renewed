package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class DropSpindleTrinket extends Trinket<DropSpindleTrinket> {
    public DropSpindleTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onHurt(LivingEntity entityLiving, DamageSource source, float amount) {
        Entity entity = source.getDirectEntity();
        if (entity instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.DROP_SPINDLE.get())) {
                for (ItemStack stack : player.getInventory().armor) {
                    if (player.level().random.nextInt(2) == 0) {
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
