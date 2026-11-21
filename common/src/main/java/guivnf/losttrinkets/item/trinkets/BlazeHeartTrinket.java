package guivnf.losttrinkets.item.trinkets;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class BlazeHeartTrinket extends Trinket<BlazeHeartTrinket> {
    public BlazeHeartTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public boolean onAttacked(Player owner, DamageSource source) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            owner.clearFire();
            return true;
        }
        return false;
    }
}
