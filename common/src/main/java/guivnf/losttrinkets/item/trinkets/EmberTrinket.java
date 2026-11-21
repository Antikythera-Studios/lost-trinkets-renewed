package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class EmberTrinket extends Trinket<EmberTrinket> {
    public EmberTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onHurt(Player owner, DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity living) {
            living.setSecondsOnFire(10);
        }
    }
}
