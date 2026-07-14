package guivnf.losttrinkets.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import guivnf.losttrinkets.client.render.entity.DarkVexRenderState;

public class DarkVexModel extends EntityModel<DarkVexRenderState> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart left_wing;
    private final ModelPart right_wing;

    public DarkVexModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_arm = root.getChild("right_arm");
        this.left_arm = root.getChild("left_arm");
        this.left_wing = root.getChild("left_wing");
        this.right_wing = root.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 18.0F, 0.0F));

        partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 10).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 16).addBox(-1.5F, 1.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(0.0F, 18.0F, 0.0F));

        partdefinition.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(23, 0).addBox(-1.5F, -0.5F, -1.0F, 2.0F, 4.0F, 2.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, 18.5F, 0.0F));

        partdefinition.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(23, 6).addBox(-0.5F, -0.5F, -1.0F, 2.0F, 4.0F, 2.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, 18.5F, 0.0F));

        partdefinition.addOrReplaceChild("left_wing",
                CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 8.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offset(0.5F, 19.0F, 1.0F));

        partdefinition.addOrReplaceChild("right_wing",
                CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 8.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offset(-0.5F, 19.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(DarkVexRenderState state) {
        super.setupAnim(state);
        this.head.yRot = state.yRot * (float) Math.PI / 180F;
        this.head.xRot = state.xRot * (float) Math.PI / 180F;

        this.right_arm.xRot = Mth.cos(state.walkAnimationPos * 0.6662F + (float) Math.PI) * 2.0F
                * state.walkAnimationSpeed * 0.5F;
        this.left_arm.xRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 2.0F * state.walkAnimationSpeed * 0.5F;

        if (state.isCharging) {
            if (state.mainArm == HumanoidArm.RIGHT) {
                this.right_arm.xRot = 3.7699115F;
            } else {
                this.left_arm.xRot = 3.7699115F;
            }
        }

        this.right_wing.yRot = 0.47123894F + Mth.cos(state.ageInTicks * 0.8F) * (float) Math.PI * 0.05F;
        this.left_wing.yRot = -this.right_wing.yRot;
    }
    // MC 26.1: Model#renderToBuffer is final and renders the root part passed to super(root).
}
