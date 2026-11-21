package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class SerpentToothTrinket extends Trinket<SerpentToothTrinket> {
    public SerpentToothTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onAttack(Player owner, LivingEntity target, DamageSource source, float amount) {
        if (source.getDirectEntity() == owner) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 0));
        }
    }
}
