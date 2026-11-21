package guivnf.losttrinkets.core.mixin;

import dev.architectury.platform.Platform;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.handler.EventHandler;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.item.trinkets.ThaCloudTrinket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
    /// The entity being hit by the current `attack` call.
    @Unique
    private Entity losttrinkets$attackTarget;

    @Inject(method = "tick", at = @At("HEAD"))
    private void losttrinkets$updateStepHeight(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        boolean active = LostTrinketsAPI.getTrinkets(self).isActive(Itms.HORSESHOE.get());
        self.setMaxUpStep(active && !self.isCrouching() ? 1.0F : 0.6F);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void losttrinkets$thaCloudClientTick(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if (self.level().isClientSide) {
            ThaCloudTrinket.clientTick(self);
        }
    }

    // HEAD, the stack is already shrunk by TAIL
    @Inject(method = "eat", at = @At("HEAD"))
    private void losttrinkets$onItemEaten(Level level, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (!level.isClientSide) {
            EventHandler.onItemEaten((Player) (Object) this, stack);
        }
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void losttrinkets$captureAttackTarget(Entity target, CallbackInfo ci) {
        this.losttrinkets$attackTarget = target;
    }

    // Fabric only, Forge uses LivingHurtEvent
    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    private float losttrinkets$modifyAttackDamage(DamageSource source, float damage) {
        if (Platform.isForge())
            return damage;
        return EventHandler.modifyAttackDamage((Player) (Object) this, this.losttrinkets$attackTarget, source, damage);
    }

    // Fabric only, Forge uses PlayerEvent.BreakSpeed
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
