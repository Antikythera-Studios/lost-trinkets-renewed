package guivnf.losttrinkets.item.trinkets;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

import java.util.List;

public class MadPiggyTrinket extends Trinket<MadPiggyTrinket> {
    public MadPiggyTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onHurt(LivingEntity entityLiving, DamageSource source, float amount) {
        Level level = entityLiving.level();
        Entity trueSource = source.getEntity();
        if (entityLiving instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trueSource instanceof LivingEntity living) {
                if (trinkets.isActive(Itms.MAD_PIGGY.get())) {
                    AABB bb = new AABB(player.blockPosition()).inflate(24.0D);
                    List<ZombifiedPiglin> entities = level.getEntitiesOfClass(ZombifiedPiglin.class, bb);
                    for (ZombifiedPiglin zombifiedPiglin : entities) {
                        zombifiedPiglin.setLastHurtByMob(living);
                        zombifiedPiglin.setTarget(living);
                        level.playSound(null, living.getX(), living.getY(), living.getZ(),
                                SoundEvents.ZOMBIFIED_PIGLIN_ANGRY, SoundSource.HOSTILE, 1.5F, 1.0F);
                    }
                }
            }
        }
        return amount;
    }
}
