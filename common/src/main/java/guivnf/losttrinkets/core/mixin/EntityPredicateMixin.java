package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.handler.TargetHandler;

import org.jetbrains.annotations.Nullable;

@Mixin(TargetingConditions.class)
public class EntityPredicateMixin {
    @Inject(method = "test", at = @At("TAIL"), cancellable = true)
    public void test(LivingEntity attacker, @Nullable LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && target != null) {
            if (TargetHandler.preventTargeting(attacker, target)) {
                cir.setReturnValue(false);
            }
        }
    }
}
