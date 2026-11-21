package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class GoldenMelonTrinket extends Trinket<GoldenMelonTrinket> {
    public GoldenMelonTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onItemEaten(Player owner, ItemStack stack) {
        FoodProperties food = stack.getItem().getFoodProperties();
        if (stack.getItem().isEdible() && food != null && food.getEffects().isEmpty()) {
            owner.heal(food.getNutrition());
        }
    }
}
