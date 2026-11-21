package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class DarkDaggerTrinket extends Trinket<DarkDaggerTrinket> {
    public DarkDaggerTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onAttack(Player owner, LivingEntity target, DamageSource source, float amount) {
        if (source.getDirectEntity() == owner) {
            owner.heal(Math.max(Math.min(amount, target.getHealth()) / 2.0F, 1.0F));
        }
    }
}
