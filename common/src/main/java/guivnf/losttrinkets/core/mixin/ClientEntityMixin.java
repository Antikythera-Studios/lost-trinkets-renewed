package guivnf.losttrinkets.core.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.item.Itms;

@Mixin(Entity.class)
public class ClientEntityMixin {

    @Inject(method = "isInvisibleTo", at = @At("RETURN"), cancellable = true)
    private void losttrinkets$mindsEye(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue())
            return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || player != mc.player)
            return;
        if (LostTrinketsAPI.getTrinkets(mc.player).isActive(Itms.MINDS_EYE.get())) {
            cir.setReturnValue(false);
        }
    }
}
