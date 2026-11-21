package guivnf.losttrinkets.item.trinkets;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class SlingshotTrinket extends Trinket<SlingshotTrinket> {
    public SlingshotTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onAttack(Player owner, LivingEntity target, DamageSource source, float amount) {
        if (source.getDirectEntity() == owner) {
            target.knockback(1.4,
                    Mth.sin(owner.getYRot() * (float) (Math.PI / 180F)),
                    -Mth.cos(owner.getYRot() * (float) (Math.PI / 180F)));
        }
    }
}
