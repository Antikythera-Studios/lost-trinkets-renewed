package guivnf.losttrinkets.core.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.client.LTHideable;
import guivnf.losttrinkets.item.Itms;

// Tha Ghost: fully hide an invisible wearer (armor/held items too). MC 26.1 renders from a render
// state, so mark the state during extraction, then cancel the submit step for marked states.
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void losttrinkets$markHidden(LivingEntity entity, LivingEntityRenderState state, float partialTick,
            CallbackInfo ci) {
        boolean hide = entity instanceof Player player
                && player.hasEffect(MobEffects.INVISIBILITY)
                && LostTrinketsAPI.getTrinkets(player).isActive(Itms.THA_GHOST.get());
        ((LTHideable) state).losttrinkets$setHidden(hide);
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At("HEAD"), cancellable = true)
    private void losttrinkets$hideRender(LivingEntityRenderState state, PoseStack poseStack,
            SubmitNodeCollector collector, CameraRenderState camera, CallbackInfo ci) {
        if (((LTHideable) state).losttrinkets$isHidden()) {
            ci.cancel();
        }
    }
}
