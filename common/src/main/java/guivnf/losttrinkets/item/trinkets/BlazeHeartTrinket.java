package guivnf.losttrinkets.item.trinkets;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.Itms;

public class BlazeHeartTrinket extends Trinket<BlazeHeartTrinket> {
    public BlazeHeartTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static boolean isImmuneToFire(LivingEntity target, DamageSource source) {
        if (target instanceof Player player) {
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.BLAZE_HEART.get())) {
                if (source.is(DamageTypeTags.IS_FIRE)) {
                    player.clearFire();
                    return true;
                }
            }
        }
        return false;
    }
}
