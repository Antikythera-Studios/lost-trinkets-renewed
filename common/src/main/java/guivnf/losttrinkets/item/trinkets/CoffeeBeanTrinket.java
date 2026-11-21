package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class CoffeeBeanTrinket extends Trinket<CoffeeBeanTrinket> {
    public CoffeeBeanTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public boolean preventEffect(Player owner, Holder<MobEffect> effect) {
        return effect == MobEffects.CONFUSION
                || effect == MobEffects.DIG_SLOWDOWN
                || effect == MobEffects.MOVEMENT_SLOWDOWN;
    }
}
