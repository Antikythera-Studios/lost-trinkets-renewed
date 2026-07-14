package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class LunchBagTrinket extends Trinket<LunchBagTrinket> {
    public LunchBagTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onItemEaten(Player player, ItemStack stack, Level level) {
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (stack.has(DataComponents.FOOD)) {
            net.minecraft.world.item.component.Consumable consumable = stack.get(DataComponents.CONSUMABLE);
            if (consumable == null || consumable.onConsumeEffects().isEmpty()) {
                if (trinkets.isActive(Itms.LUNCH_BAG.get()) && level.getRandom().nextInt(10) == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.SATURATION,
                            level.getRandom().nextInt(200) + 100, 1, false, false));
                }
            }
        }
    }
}
