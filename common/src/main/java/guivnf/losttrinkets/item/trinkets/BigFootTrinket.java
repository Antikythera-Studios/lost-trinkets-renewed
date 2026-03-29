package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.ITargetingTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.core.mixin.MobAccessor;
import guivnf.losttrinkets.entity.ai.BigFootGoal;

public class BigFootTrinket extends Trinket<BigFootTrinket> implements ITargetingTrinket {
    public BigFootTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void addAvoidGoal(Entity entity) {
        if (entity instanceof PathfinderMob mob) {
            ((MobAccessor) mob).getGoalSelector().addGoal(-1, new BigFootGoal(mob));
        }
    }

    @Override
    public boolean preventTargeting(Mob mob, Player player, boolean notAttacked) {
        return mob.isBaby();
    }
}
