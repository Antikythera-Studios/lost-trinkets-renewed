package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class EmberTrinket extends Trinket<EmberTrinket> {
    public EmberTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onHurt(LivingEntity entityLiving, DamageSource source, float amount) {
        Entity immediateSource = source.getEntity();
        if (immediateSource instanceof LivingEntity living) {
            if (entityLiving instanceof Player player) {
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                if (trinkets.isActive(Itms.EMBER.get())) {
                    living.igniteForSeconds(10.0F);
                }
            }
        }
        return amount;
    }
}
