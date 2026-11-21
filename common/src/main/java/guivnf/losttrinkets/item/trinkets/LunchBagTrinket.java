package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class LunchBagTrinket extends Trinket<LunchBagTrinket> {
    public LunchBagTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onItemEaten(Player owner, ItemStack stack) {
        Consumable consumable = stack.get(DataComponents.CONSUMABLE);
        RandomSource random = owner.level().getRandom();
        if (stack.has(DataComponents.FOOD) && (consumable == null || consumable.onConsumeEffects().isEmpty())
                && random.nextInt(10) == 0) {
            owner.addEffect(new MobEffectInstance(MobEffects.SATURATION, random.nextInt(200) + 100, 1, false, false));
        }
    }
}
