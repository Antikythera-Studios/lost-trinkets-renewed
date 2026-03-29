package guivnf.losttrinkets.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.client.model.DarkVexModel;
import guivnf.losttrinkets.entity.DarkVexEntity;

public class DarkVexRenderer extends MobRenderer<DarkVexEntity, DarkVexModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(LostTrinkets.MOD_ID,
            "textures/entity/dark_vex.png");

    public DarkVexRenderer(EntityRendererProvider.Context context) {
        super(context, new DarkVexModel(context.bakeLayer(DarkVexModelLayer.DARK_VEX)), 0.3F);
    }

    @Override
    protected int getBlockLightLevel(DarkVexEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(DarkVexEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(DarkVexEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(0.4F, 0.4F, 0.4F);
    }
}
