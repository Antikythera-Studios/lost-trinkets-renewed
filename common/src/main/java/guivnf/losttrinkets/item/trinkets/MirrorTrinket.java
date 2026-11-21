package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class MirrorTrinket extends Trinket<MirrorTrinket> {
    /// Set while reflecting, so two mirrored players don't bounce damage forever.
    private static final ThreadLocal<Boolean> REFLECTING = ThreadLocal.withInitial(() -> false);

    public MirrorTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onHurt(Player owner, DamageSource source, float amount) {
        if (REFLECTING.get() || !(source.getEntity() instanceof LivingEntity living))
            return;
        try {
            REFLECTING.set(true);
            living.hurt(owner.damageSources().playerAttack(owner), amount / 2.0F);
        } finally {
            REFLECTING.set(false);
        }
    }
}
