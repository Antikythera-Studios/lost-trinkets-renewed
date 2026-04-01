package guivnf.losttrinkets.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.item.Itms;

public class ClientEventHandler {
    public static boolean onRenderLivingPre(LivingEntity living, LivingEntityRenderer renderer,
            PoseStack matrix, MultiBufferSource bufferSource,
            float partialTicks, int packedLight) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null)
            return false;

        if (living instanceof Player player) {
            if (player.hasEffect(MobEffects.INVISIBILITY)
                    && LostTrinketsAPI.getTrinkets(player).isActive(Itms.THA_GHOST.get())) {
                return true;
            }
        }
        return false;
    }
}
