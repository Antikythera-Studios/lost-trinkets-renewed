package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.item.trinkets.TrebleHooksTrinket;

@Mixin(FishingHook.class)
public class FishingHookMixin {
    @Shadow
    private int nibble;

    @Inject(method = "retrieve", at = @At("HEAD"))
    private void losttrinkets$trebleHooks(ItemStack rod, CallbackInfoReturnable<Integer> cir) {
        FishingHook hook = (FishingHook) (Object) this;
        if (hook.level().isClientSide())
            return;
        if (hook.getHookedIn() != null || this.nibble <= 0)
            return;
        Player player = hook.getPlayerOwner();
        if (player != null) {
            TrebleHooksTrinket.onFished(player, hook);
        }
    }
}
