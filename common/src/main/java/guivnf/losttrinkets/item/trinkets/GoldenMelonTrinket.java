package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.component.DataComponents;
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
        if (stack.has(DataComponents.FOOD)) {
            FoodProperties food = stack.get(DataComponents.FOOD);
            net.minecraft.world.item.component.Consumable consumable = stack.get(DataComponents.CONSUMABLE);
            boolean noEffects = consumable == null || consumable.onConsumeEffects().isEmpty();
            if (food != null && noEffects) {
                if (trinkets.isActive(Itms.GOLDEN_MELON.get())) {
                    player.heal(food.nutrition());
                }
            }
        }
    }
}
