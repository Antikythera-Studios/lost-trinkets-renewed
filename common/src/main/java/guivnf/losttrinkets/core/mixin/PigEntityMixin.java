package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.item.Itms;

@Mixin(Pig.class)
public abstract class PigEntityMixin extends Animal {
    protected PigEntityMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void losttrinkets$allowRideWithoutSaddle(Player player, InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> cir) {
        Pig self = (Pig) (Object) this;
        if (player.isSecondaryUseActive() || self.isVehicle())
            return;
        ItemStack held = player.getItemInHand(hand);

        if (held.is(Items.SADDLE) || self.isFood(held))
            return;
        if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.PIGGY.get())) {
            if (!self.level().isClientSide()) {
                player.startRiding(self);
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(method = "getControllingPassenger", at = @At("RETURN"), cancellable = true)
    private void losttrinkets$modifyControllingPassenger(CallbackInfoReturnable<Entity> cir) {
        if (cir.getReturnValue() != null)
            return;
        Entity first = this.getFirstPassenger();
        if (first instanceof Player player) {
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.PIGGY.get())) {
                cir.setReturnValue(player);
            }
        }
    }

    @Inject(method = "getRiddenSpeed", at = @At("RETURN"), cancellable = true)
    private void losttrinkets$modifySaddledSpeed(Player player, CallbackInfoReturnable<Float> cir) {
        // MC 26.1's getRiddenSpeed returns a much smaller value than the old getSaddledSpeed
        // (movementSpeed * 0.225 * boost), so scale the current speed instead of hard-setting it.
        if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.PIGGY.get())) {
            cir.setReturnValue(cir.getReturnValueF() * 2.0F);
        }
    }
}
