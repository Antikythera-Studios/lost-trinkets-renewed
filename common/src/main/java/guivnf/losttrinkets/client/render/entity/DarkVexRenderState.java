package guivnf.losttrinkets.client.render.entity;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.HumanoidArm;

/**
 * Render state for {@link guivnf.losttrinkets.entity.DarkVexEntity}. MC 26.1 renders entities from an
 * extracted state object rather than the live entity, so the model reads charging/arm data from here.
 */
public class DarkVexRenderState extends LivingEntityRenderState {
    public boolean isCharging;
    public HumanoidArm mainArm = HumanoidArm.RIGHT;
}
