package guivnf.losttrinkets.entity;

import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.UUID;

public class DarkEntity extends PathfinderMob {
    @Nullable
    protected UUID owner;
    @Nullable
    protected Player player;

    public DarkEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.owner == null) {
                vanish();
            } else {
                Player found = ((ServerLevel) this.level()).getServer().getPlayerList().getPlayer(this.owner);
                if (found != null) {
                    this.player = found;
                } else {
                    vanish();
                }
            }
            if (getTarget() == null || !getTarget().isAlive()) {
                List<Mob> entities = this.level().getEntitiesOfClass(Mob.class, new AABB(blockPosition()).inflate(24));
                boolean flag = false;
                for (Mob entity : entities) {
                    if (entity.getTarget() != null) {
                        if (this.owner != null && this.owner.equals(entity.getTarget().getUUID())) {
                            setTarget(entity);
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag)
                    vanish();
            }
        }
    }

    protected void vanish() {
        discard();
        spawnAnim();
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.owner = input.read("owner", UUIDUtil.CODEC).orElse(null);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (this.owner != null) {
            output.store("owner", UUIDUtil.CODEC, this.owner);
        }
    }

    @Nullable
    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner.getUUID();
    }
}
