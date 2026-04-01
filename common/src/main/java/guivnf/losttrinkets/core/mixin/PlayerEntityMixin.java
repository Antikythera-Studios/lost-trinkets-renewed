package guivnf.losttrinkets.core.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.handler.EventHandler;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.item.trinkets.ThaCloudTrinket;
import guivnf.losttrinkets.item.trinkets.ThaSpiderTrinket;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private static final ResourceLocation HORSESHOE_STEP_ID = ResourceLocation.fromNamespaceAndPath(LostTrinkets.MOD_ID, "horseshoe_step_height");

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean onClimbable() {
        if (!super.onClimbable()) {
            return ThaSpiderTrinket.doClimb((Player) (Object) this);
        }
        return true;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void losttrinkets$updateStepHeight(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        boolean active = LostTrinketsAPI.getTrinkets(self).isActive(Itms.HORSESHOE.get());
        var stepHeight = self.getAttribute(Attributes.STEP_HEIGHT);
        if (stepHeight != null) {
            if (active && !self.isCrouching()) {
                if (!stepHeight.hasModifier(HORSESHOE_STEP_ID)) {
                    stepHeight.addTransientModifier(new AttributeModifier(HORSESHOE_STEP_ID, 0.4, AttributeModifier.Operation.ADD_VALUE));
                }
            } else {
                stepHeight.removeModifier(HORSESHOE_STEP_ID);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void losttrinkets$thaCloudClientTick(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if (self.level().isClientSide) {
            ThaCloudTrinket.clientTick(self);
        }
    }

    @Inject(method = "eat", at = @At("TAIL"))
    private void losttrinkets$onItemEaten(Level level, ItemStack stack, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
        if (!level.isClientSide) {
            EventHandler.onItemEaten((Player) (Object) this, stack, level);
        }
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    private float losttrinkets$scaleDamage(float damage) {
        if (dev.architectury.platform.Platform.isNeoForge())
            return damage;
        Player self = (Player) (Object) this;
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(self);
        if (trinkets.isActive(Itms.SILVER_NAIL.get()))
            damage *= 1.1F;
        if (trinkets.isActive(Itms.GLORY_SHARDS.get()))
            damage *= 1.2F;
        return damage;
    }

    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    private void losttrinkets$minersPickBreakSpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        if (!dev.architectury.platform.Platform.isFabric())
            return;
        float newSpeed = EventHandler.onBreakSpeed((Player) (Object) this, cir.getReturnValueF());
        if (newSpeed != cir.getReturnValueF()) {
            cir.setReturnValue(newSpeed);
        }
    }
}
