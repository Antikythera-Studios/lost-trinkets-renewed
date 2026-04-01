package guivnf.losttrinkets.item.trinkets;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.entity.DarkVexEntity;
import guivnf.losttrinkets.entity.Entities;
import guivnf.losttrinkets.item.Itms;

public class DarkEggTrinket extends Trinket<DarkEggTrinket> {
    public DarkEggTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onHurt(LivingEntity entityLiving, DamageSource source, float amount) {
        Level level = entityLiving.level();
        Entity trueSource = source.getEntity();
        if (entityLiving instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trueSource instanceof LivingEntity living) {
                if (trinkets.isActive(Itms.DARK_EGG.get())) {
                    AABB bb = new AABB(player.blockPosition()).inflate(16);
                    int vexCount = level.getEntitiesOfClass(DarkVexEntity.class, bb).size();
                    if (vexCount < 6 && level instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 3; i++) {
                            DarkVexEntity vex = Entities.DARK_VEX.get().create(serverLevel);
                            if (vex != null) {
                                vex.finalizeSpawn(serverLevel,
                                        serverLevel.getCurrentDifficultyAt(player.blockPosition()),
                                        MobSpawnType.MOB_SUMMONED, null);
                                vex.setTarget(living);
                                vex.setOwner(player);
                                vex.setBoundOrigin(player.blockPosition());
                                vex.moveTo(player.getX(), player.getY(), player.getZ());
                                serverLevel.addFreshEntity(vex);
                            }
                        }
                    }
                }
            }
        }
        return amount;
    }
}
