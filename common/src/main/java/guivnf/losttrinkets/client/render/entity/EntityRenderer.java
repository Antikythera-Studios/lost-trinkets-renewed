package guivnf.losttrinkets.client.render.entity;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import guivnf.losttrinkets.entity.Entities;

public class EntityRenderer {
    public static void register() {
        // on Forge, registered via EntityRenderersEvent in ForgeClientEventHandler to
        // ensure correct timing
        if (!dev.architectury.platform.Platform.isForge()) {
            EntityRendererRegistry.register(Entities.DARK_VEX, DarkVexRenderer::new);
        }
    }
}
