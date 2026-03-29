package guivnf.losttrinkets.core.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.item.Itms;

@Mixin(LivingEntityRenderer.class)
public class ClientLivingEntityRendererMixin {

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void losttrinkets$onRenderHead(LivingEntity entity, float entityYaw, float partialTick,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight,
            CallbackInfo ci) {
        if (Platform.isForge())
            return; // forge handles this via RenderLivingEvent.Pre
        if (!(entity instanceof Player player))
            return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;
        if (player.hasEffect(MobEffects.INVISIBILITY)
                && LostTrinketsAPI.getTrinkets(player).isActive(Itms.THA_GHOST.get())) {
            ci.cancel();
        }
    }
}
