package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class StarfishTrinket extends Trinket<StarfishTrinket> {
    public StarfishTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onAttack(Player owner, LivingEntity target, DamageSource source, float amount) {
        if (target instanceof Monster && source.getDirectEntity() == owner) {
            owner.giveExperiencePoints(Math.max(1, (int) (amount / 2.0F)));
        }
    }
}
