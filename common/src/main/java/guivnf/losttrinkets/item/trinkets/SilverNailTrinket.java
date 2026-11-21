package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class SilverNailTrinket extends Trinket<SilverNailTrinket> {
    public SilverNailTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public float modifyAttackDamage(Player owner, Entity target, DamageSource source, float amount) {
        return amount * 1.1F;
    }
}
