package guivnf.losttrinkets.core.mixin;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import guivnf.losttrinkets.client.LTHideable;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements LTHideable {
    @Unique
    private boolean losttrinkets$hidden;

    @Override
    public boolean losttrinkets$isHidden() {
        return this.losttrinkets$hidden;
    }

    @Override
    public void losttrinkets$setHidden(boolean hidden) {
        this.losttrinkets$hidden = hidden;
    }
}
