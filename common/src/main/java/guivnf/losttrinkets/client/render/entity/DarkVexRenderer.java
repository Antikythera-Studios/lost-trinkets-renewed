package guivnf.losttrinkets.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.client.model.DarkVexModel;
import guivnf.losttrinkets.entity.DarkVexEntity;

public class DarkVexRenderer extends MobRenderer<DarkVexEntity, DarkVexRenderState, DarkVexModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID,
            "textures/entity/dark_vex.png");

    public DarkVexRenderer(EntityRendererProvider.Context context) {
        super(context, new DarkVexModel(context.bakeLayer(DarkVexModelLayer.DARK_VEX)), 0.3F);
    }

    @Override
    public DarkVexRenderState createRenderState() {
        return new DarkVexRenderState();
    }

    @Override
    public void extractRenderState(DarkVexEntity entity, DarkVexRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isCharging = entity.isCharging();
        state.mainArm = entity.getMainArm();
    }

    @Override
    protected int getBlockLightLevel(DarkVexEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public Identifier getTextureLocation(DarkVexRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(DarkVexRenderState state, PoseStack poseStack) {
        poseStack.scale(0.4F, 0.4F, 0.4F);
    }
}
