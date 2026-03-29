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

public class MirrorTrinket extends Trinket<MirrorTrinket> {
    private static final ThreadLocal<Boolean> dealingMirrorDamage = ThreadLocal.withInitial(() -> false);

    public MirrorTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onHurt(LivingEntity entityLiving, DamageSource source, float amount) {
        if (dealingMirrorDamage.get())
            return amount;
        try {
            dealingMirrorDamage.set(true);
            return mirrorDamage(entityLiving, source, amount);
        } finally {
            dealingMirrorDamage.set(false);
        }
    }

    private static float mirrorDamage(LivingEntity entityLiving, DamageSource source, float amount) {
        Entity trueSource = source.getEntity();
        if (entityLiving instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trueSource instanceof LivingEntity living) {
                if (trinkets.isActive(Itms.MIRROR.get())) {
                    living.hurt(player.damageSources().playerAttack(player), amount / 2.0F);
                }
            }
        }
        return amount;
    }
}
