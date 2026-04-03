package guivnf.losttrinkets.core.mixin;

import guivnf.losttrinkets.handler.EventHandler;
import guivnf.losttrinkets.item.trinkets.ThaSpiderTrinket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    // also handled by MobEffectEvent.Applicable on Forge inject at RETURN so we
    // only override when still true
    @Inject(method = "canBeAffected", at = @At("RETURN"), cancellable = true)
    private void losttrinkets$denyMobEffect(MobEffectInstance effectInstance,
            CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && (Object) this instanceof Player player) {
            if (EventHandler.shouldDenyMobEffect(player, effectInstance.getEffect())) {
                cir.setReturnValue(false);
            }
        }
    }

    // fabric only on Forge, LivingTickEvent in ForgeEventHandler handles this
    @Inject(method = "tick", at = @At("HEAD"))
    private void losttrinkets$livingTick(CallbackInfo ci) {
        if (!dev.architectury.platform.Platform.isFabric())
            return;
        LivingEntity self = (LivingEntity) (Object) this;
        if (!self.level().isClientSide() && !(self instanceof Player)) {
            EventHandler.onLivingUpdate(self);
        }
    }

    // fabric only on Forge, LivingDropsEvent in ForgeEventHandler handles this
    @Inject(method = "die", at = @At("HEAD"))
    private void losttrinkets$extraDrops(DamageSource source, CallbackInfo ci) {
        if (dev.architectury.platform.Platform.isForge())
            return;
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.level().isClientSide())
            return;

        Entity killer = source.getEntity();
        if (!(killer instanceof Player player))
            return;

        if (!(self.level() instanceof ServerLevel serverLevel))
            return;

        List<ItemStack> extras = EventHandler.getExtraDrops(player, self);
        for (ItemStack stack : extras) {

            serverLevel.addFreshEntity(
                    new ItemEntity(serverLevel, self.getX(), self.getY(), self.getZ(), stack));
        }
    }

    @Inject(method = "onClimbable", at = @At("RETURN"), cancellable = true)
    private void losttrinkets$thaSpiderTrinket(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) {
            if ((Object) this instanceof Player player && ThaSpiderTrinket.doClimb(player)) {
                cir.setReturnValue(true);
            }
        }
    }
}
