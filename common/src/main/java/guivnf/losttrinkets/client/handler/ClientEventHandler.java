package guivnf.losttrinkets.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class ClientEventHandler {
    // MC 26.1 renders living entities from an extracted render state, which no longer exposes the
    // live entity here. The THA_GHOST trinket's "fully hide an invisible player" effect therefore
    // needs reimplementation via NeoForge's BaseRenderState data attachment (flag set during
    // extractRenderState). Temporarily inert so rendering is unaffected.
    public static boolean onRenderLivingPre(LivingEntityRenderState state, LivingEntityRenderer renderer,
            PoseStack matrix, float partialTicks) {
        return false;
    }
}
