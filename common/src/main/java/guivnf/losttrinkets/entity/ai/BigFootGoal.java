package guivnf.losttrinkets.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.item.Itms;

import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;

public class BigFootGoal extends Goal {
    public static final double SPEED = 1.4;
    protected final PathNavigation navigation;
    private final PathfinderMob entity;
    @Nullable
    protected net.minecraft.world.level.pathfinder.Path path;
    @Nullable
    protected Player player;

    public BigFootGoal(PathfinderMob entity) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.navigation = entity.getNavigation();
        this.entity = entity;
    }

    @Override
    public void start() {
        this.navigation.moveTo(this.path, SPEED);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.navigation.isDone();
    }

    @Override
    public void stop() {
        this.player = null;
    }

    @Override
    public void tick() {
        this.navigation.setSpeedModifier(SPEED);
    }

    @Override
    public boolean canUse() {
        if (!isBoss(this.entity) && this.entity.isBaby()) {
            this.player = this.entity.level().getNearestPlayer(
                    this.entity.getX(), this.entity.getY(), this.entity.getZ(), 8.0,
                    target -> target instanceof Player
                            && LostTrinketsAPI.getTrinkets((Player) target).isActive(Itms.BIG_FOOT.get()));
            if (this.player != null) {
                Vec3 vector3d = DefaultRandomPos.getPosAway(this.entity, 16, 7, this.player.position());
                if (vector3d == null) {
                    return false;
                } else if (this.player.distanceToSqr(vector3d.x, vector3d.y, vector3d.z) < this.player
                        .distanceToSqr(this.entity)) {
                    return false;
                } else {
                    this.path = this.navigation.createPath(vector3d.x, vector3d.y, vector3d.z, 0);
                    return this.path != null;
                }
            }
        }
        return false;
    }

    private static boolean isBoss(net.minecraft.world.entity.Mob mob) {
        return mob instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon
                || mob instanceof net.minecraft.world.entity.boss.wither.WitherBoss;
    }
}
