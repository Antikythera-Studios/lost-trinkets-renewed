package guivnf.losttrinkets.core.mixin;

import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.handler.EventHandler;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.item.trinkets.ThaCloudTrinket;
import net.minecraft.world.entity.player.Player;
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

    // runs on both loaders via mixin Forge's onUseFinish was removed to avoid
    // double-triggering
    @Inject(method = "eat", at = @At("TAIL"))
    private void losttrinkets$onItemEaten(Level level, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (!level.isClientSide) {
            EventHandler.onItemEaten((Player) (Object) this, stack, level);
        }
    }

    // fabric only , on Forge, EventHandler.onHurt() return value is used by
    // LivingHurtEvent.setAmount()
    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    private float losttrinkets$scaleDamage(float damage) {
        if (dev.architectury.platform.Platform.isForge())
            return damage;
        Player self = (Player) (Object) this;
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(self);
        if (trinkets.isActive(Itms.SILVER_NAIL.get()))
            damage *= 1.1F;
        if (trinkets.isActive(Itms.GLORY_SHARDS.get()))
            damage *= 1.2F;
        return damage;
    }

    // fabric only, on Forge, PlayerEvent.BreakSpeed in ForgeEventHandler handles
    // this
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
