package guivnf.losttrinkets.item.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class MadPiggyTrinket extends Trinket<MadPiggyTrinket> {
    public MadPiggyTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onHurt(Player owner, DamageSource source, float amount) {
        if (!(source.getEntity() instanceof LivingEntity living))
            return;
        Level level = owner.level();
        AABB bb = new AABB(owner.blockPosition()).inflate(24.0D);
        for (ZombifiedPiglin zombifiedPiglin : level.getEntitiesOfClass(ZombifiedPiglin.class, bb)) {
            zombifiedPiglin.setLastHurtByMob(living);
            zombifiedPiglin.setTarget(living);
            level.playSound(null, living.getX(), living.getY(), living.getZ(),
                    SoundEvents.ZOMBIFIED_PIGLIN_ANGRY, SoundSource.HOSTILE, 1.5F, 1.0F);
        }
    }
}
