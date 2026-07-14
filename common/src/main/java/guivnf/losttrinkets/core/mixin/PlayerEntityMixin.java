package guivnf.losttrinkets.core.mixin;

import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.handler.EventHandler;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.item.trinkets.ThaCloudTrinket;
import net.minecraft.resources.Identifier;
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

@Mixin(Player.class)
public abstract class PlayerEntityMixin {
    private static final Identifier HORSESHOE_STEP_ID = Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, "horseshoe_step_height");

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

    // MC 26.1 removed Player#eat; the "item eaten" hook now lives in ConsumableMixin (Consumable#onConsume).

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    private float losttrinkets$scaleDamage(float damage) {
        if (dev.architectury.platform.Platform.isNeoForge())
            return damage;
        Player self = (Player) (Object) this;
        return damage * guivnf.losttrinkets.handler.EventHandler.getOutgoingDamageMultiplier(self);
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
