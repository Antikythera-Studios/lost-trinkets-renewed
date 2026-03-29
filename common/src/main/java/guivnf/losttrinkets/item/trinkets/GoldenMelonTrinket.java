package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class GoldenMelonTrinket extends Trinket<GoldenMelonTrinket> {
    public GoldenMelonTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onItemEaten(Player player, ItemStack stack, Level level) {
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (stack.getItem().isEdible()) {
            FoodProperties food = stack.getItem().getFoodProperties();
            if (food != null && food.getEffects().isEmpty()) {
                if (trinkets.isActive(Itms.GOLDEN_MELON.get())) {
                    player.heal(food.getNutrition());
                }
            }
        }
    }
}
