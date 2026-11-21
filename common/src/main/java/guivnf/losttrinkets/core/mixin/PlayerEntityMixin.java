package guivnf.losttrinkets.core.mixin;

import dev.architectury.platform.Platform;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.handler.EventHandler;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.item.trinkets.ThaCloudTrinket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {
    private static final Identifier HORSESHOE_STEP_ID = Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, "horseshoe_step_height");

    @Unique
    private Entity losttrinkets$attackTarget;

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
        if (self.level().isClientSide()) {
            ThaCloudTrinket.clientTick(self);
        }
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void losttrinkets$captureAttackTarget(Entity target, CallbackInfo ci) {
        this.losttrinkets$attackTarget = target;
    }

    // Fabric only, NeoForge uses LivingIncomingDamageEvent
    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    private float losttrinkets$modifyAttackDamage(DamageSource source, float damage) {
        if (Platform.isNeoForge())
            return damage;
        return EventHandler.modifyAttackDamage((Player) (Object) this, this.losttrinkets$attackTarget, source, damage);
    }

    // Fabric only, NeoForge uses PlayerEvent.BreakSpeed
    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    private void losttrinkets$modifyBreakSpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        if (!Platform.isFabric())
            return;
        float speed = EventHandler.onBreakSpeed((Player) (Object) this, state, cir.getReturnValueF());
        if (speed != cir.getReturnValueF()) {
            cir.setReturnValue(speed);
        }
    }
}
