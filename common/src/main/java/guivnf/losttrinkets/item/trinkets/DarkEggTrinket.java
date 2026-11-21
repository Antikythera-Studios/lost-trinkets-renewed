package guivnf.losttrinkets.item.trinkets;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.entity.DarkVexEntity;
import guivnf.losttrinkets.entity.Entities;

public class DarkEggTrinket extends Trinket<DarkEggTrinket> {
    public DarkEggTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onHurt(Player owner, DamageSource source, float amount) {
        if (!(source.getEntity() instanceof LivingEntity living) || !(owner.level() instanceof ServerLevel serverLevel))
            return;
        AABB bb = new AABB(owner.blockPosition()).inflate(16);
        if (serverLevel.getEntitiesOfClass(DarkVexEntity.class, bb).size() >= 6)
            return;
        for (int i = 0; i < 3; i++) {
            DarkVexEntity vex = Entities.DARK_VEX.get().create(serverLevel);
            if (vex != null) {
                vex.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(owner.blockPosition()),
                        MobSpawnType.MOB_SUMMONED, null);
                vex.setTarget(living);
                vex.setOwner(owner);
                vex.setBoundOrigin(owner.blockPosition());
                vex.moveTo(owner.getX(), owner.getY(), owner.getZ());
                serverLevel.addFreshEntity(vex);
            }
        }
    }
}
