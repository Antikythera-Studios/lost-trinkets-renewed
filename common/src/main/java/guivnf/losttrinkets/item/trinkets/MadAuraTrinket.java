package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class MadAuraTrinket extends Trinket<MadAuraTrinket> {
    public MadAuraTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static boolean shouldCancelAttack(LivingEntity entityLiving, DamageSource source) {
        Entity immediateSource = source.getDirectEntity();
        if (entityLiving instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (immediateSource instanceof AbstractArrow) {
                if (trinkets.isActive(Itms.MAD_AURA.get())) {
                    return true;
                }
            }
        }
        return false;
    }
}
