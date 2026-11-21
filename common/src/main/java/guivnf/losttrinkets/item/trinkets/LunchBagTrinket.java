package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.RandomSource;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class LunchBagTrinket extends Trinket<LunchBagTrinket> {
    public LunchBagTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onItemEaten(Player owner, ItemStack stack) {
        FoodProperties food = stack.getItem().getFoodProperties();
        RandomSource random = owner.level().random;
        if (stack.getItem().isEdible() && food != null && food.getEffects().isEmpty() && random.nextInt(10) == 0) {
            owner.addEffect(new MobEffectInstance(MobEffects.SATURATION, random.nextInt(200) + 100, 1, false, false));
        }
    }
}
