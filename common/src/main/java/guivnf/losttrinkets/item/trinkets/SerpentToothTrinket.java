package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class SerpentToothTrinket extends Trinket<SerpentToothTrinket> {
    public SerpentToothTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onHurt(LivingEntity entityLiving, DamageSource source, float amount) {
        Entity entity = source.getDirectEntity();
        if (entity instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.SERPENT_TOOTH.get())) {
                entityLiving.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 0));
            }
        }
        return amount;
    }
}
