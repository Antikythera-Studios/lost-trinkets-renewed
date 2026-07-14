package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.item.trinkets.StickyMindTrinket;

@Mixin(EnderMan.class)
public class EndermanMixin {
    @Inject(method = "isBeingStaredBy", at = @At("RETURN"), cancellable = true)
    private void losttrinkets$blankEyes(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.BLANK_EYES.get())) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "teleport()Z", at = @At("HEAD"), cancellable = true)
    private void losttrinkets$stickyMind(CallbackInfoReturnable<Boolean> cir) {
        EnderMan self = (EnderMan) (Object) this;
        if (StickyMindTrinket.shouldPreventTeleport(self)) {
            cir.setReturnValue(false);
        }
    }
}
