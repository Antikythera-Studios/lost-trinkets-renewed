package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class CoffeeBeanTrinket extends Trinket<CoffeeBeanTrinket> {
    public CoffeeBeanTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static boolean shouldDenyEffect(Player player, MobEffect effect) {
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (trinkets.isActive(Itms.COFFEE_BEAN.get())) {
            return effect == MobEffects.CONFUSION
                    || effect == MobEffects.DIG_SLOWDOWN
                    || effect == MobEffects.MOVEMENT_SLOWDOWN;
        }
        return false;
    }
}
