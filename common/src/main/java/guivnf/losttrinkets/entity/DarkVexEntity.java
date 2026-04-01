package guivnf.losttrinkets.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;

public class DarkVexEntity extends DarkEntity {
    protected static final EntityDataAccessor<Byte> VEX_FLAGS = SynchedEntityData.defineId(DarkVexEntity.class,
            EntityDataSerializers.BYTE);
    @Nullable
    private BlockPos boundOrigin;

    public DarkVexEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.moveControl = new DarkVexEntity.VexMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new DarkVexEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(8, new DarkVexEntity.MoveRandomGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Mob.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Player.class).setAlertOthers());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VEX_FLAGS, (byte) 0);
    }

    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        setNoGravity(true);
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    public void setBoundOrigin(@Nullable BlockPos pos) {
        this.boundOrigin = pos;
    }

    private boolean getVexFlag(int mask) {
        int i = this.entityData.get(VEX_FLAGS);
        return (i & mask) != 0;
    }

    private void setVexFlag(int mask, boolean value) {
        int i = this.entityData.get(VEX_FLAGS);
        if (value) {
            i = i | mask;
        } else {
            i = i & ~mask;
        }
        this.entityData.set(VEX_FLAGS, (byte) (i & 255));
    }

    public boolean isCharging() {
        return getVexFlag(1);
    }

    public void setCharging(boolean charging) {
        setVexFlag(1, charging);
    }

    @Override
    public void playAmbientSound() {
        if (this.random.nextInt(7) == 0) {
            super.playAmbientSound();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VEX_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.VEX_HURT;
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData data) {
        populateDefaultEquipmentSlots(this.random, difficulty);
        populateDefaultEquipmentEnchantments(level, this.random, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, data);
    }

    @Override
    protected void populateDefaultEquipmentSlots(net.minecraft.util.RandomSource random,
            DifficultyInstance difficulty) {
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    class ChargeAttackGoal extends Goal {
        public ChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return DarkVexEntity.this.getTarget() != null
                    && !DarkVexEntity.this.getMoveControl().hasWanted()
                    && DarkVexEntity.this.random.nextInt(7) == 0
                    && DarkVexEntity.this.distanceToSqr(DarkVexEntity.this.getTarget()) > 4.0D;
        }

        @Override
        public boolean canContinueToUse() {
            return DarkVexEntity.this.getMoveControl().hasWanted()
                    && DarkVexEntity.this.isCharging()
                    && DarkVexEntity.this.getTarget() != null
                    && DarkVexEntity.this.getTarget().isAlive();
        }

        @Override
        public void start() {
            LivingEntity target = DarkVexEntity.this.getTarget();
            if (target != null) {
                Vec3 eyePos = target.getEyePosition(1.0F);
                DarkVexEntity.this.moveControl.setWantedPosition(eyePos.x, eyePos.y, eyePos.z, 1.0D);
                DarkVexEntity.this.setCharging(true);
                DarkVexEntity.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
            }
        }

        @Override
        public void stop() {
            DarkVexEntity.this.setCharging(false);
        }

        @Override
        public void tick() {
            LivingEntity target = DarkVexEntity.this.getTarget();
            if (target != null) {
                if (DarkVexEntity.this.getBoundingBox().intersects(target.getBoundingBox())) {
                    DarkVexEntity.this.doHurtTarget(target);
                    DarkVexEntity.this.setCharging(false);
                } else {
                    double distSq = DarkVexEntity.this.distanceToSqr(target);
                    if (distSq < 9.0D) {
                        Vec3 eyePos = target.getEyePosition(1.0F);
                        DarkVexEntity.this.moveControl.setWantedPosition(eyePos.x, eyePos.y, eyePos.z, 1.0D);
                    }
                }
            }
        }
    }

    class VexMoveControl extends MoveControl {
        public VexMoveControl(DarkVexEntity vex) {
            super(vex);
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 delta = new Vec3(this.wantedX - DarkVexEntity.this.getX(),
                        this.wantedY - DarkVexEntity.this.getY(),
                        this.wantedZ - DarkVexEntity.this.getZ());
                double len = delta.length();
                if (len < DarkVexEntity.this.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    DarkVexEntity.this.setDeltaMovement(DarkVexEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    DarkVexEntity.this.setDeltaMovement(
                            DarkVexEntity.this.getDeltaMovement().add(delta.scale(this.speedModifier * 0.05D / len)));
                    if (DarkVexEntity.this.getTarget() == null) {
                        Vec3 motion = DarkVexEntity.this.getDeltaMovement();
                        DarkVexEntity.this.setYRot(-((float) Mth.atan2(motion.x, motion.z)) * (180F / (float) Math.PI));
                        DarkVexEntity.this.yBodyRot = DarkVexEntity.this.getYRot();
                    } else {
                        double dx = DarkVexEntity.this.getTarget().getX() - DarkVexEntity.this.getX();
                        double dz = DarkVexEntity.this.getTarget().getZ() - DarkVexEntity.this.getZ();
                        DarkVexEntity.this.setYRot(-((float) Mth.atan2(dx, dz)) * (180F / (float) Math.PI));
                        DarkVexEntity.this.yBodyRot = DarkVexEntity.this.getYRot();
                    }
                }
            }
        }
    }

    class MoveRandomGoal extends Goal {
        public MoveRandomGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !DarkVexEntity.this.getMoveControl().hasWanted() && DarkVexEntity.this.random.nextInt(7) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void tick() {
            BlockPos origin = DarkVexEntity.this.getBoundOrigin();
            if (origin == null) {
                origin = DarkVexEntity.this.blockPosition();
            }
            for (int i = 0; i < 3; ++i) {
                BlockPos pos = origin.offset(
                        DarkVexEntity.this.random.nextInt(15) - 7,
                        DarkVexEntity.this.random.nextInt(11) - 5,
                        DarkVexEntity.this.random.nextInt(15) - 7);
                if (DarkVexEntity.this.level().isEmptyBlock(pos)) {
                    DarkVexEntity.this.moveControl.setWantedPosition(pos.getX() + 0.5D, pos.getY() + 0.5D,
                            pos.getZ() + 0.5D, 0.25D);
                    if (DarkVexEntity.this.getTarget() == null) {
                        DarkVexEntity.this.getLookControl().setLookAt(pos.getX() + 0.5D, pos.getY() + 0.5D,
                                pos.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }
        }
    }
}
