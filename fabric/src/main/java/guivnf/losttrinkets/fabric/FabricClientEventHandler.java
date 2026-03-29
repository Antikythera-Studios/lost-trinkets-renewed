package guivnf.losttrinkets.fabric;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import guivnf.losttrinkets.client.handler.KeyHandler;
import guivnf.losttrinkets.client.handler.hud.HudHandler;
import guivnf.losttrinkets.client.model.DarkVexModel;
import guivnf.losttrinkets.client.render.entity.DarkVexModelLayer;
import guivnf.losttrinkets.handler.UnlockManager;

@Environment(EnvType.CLIENT)
public class FabricClientEventHandler {

    public static void register() {
        ClientTickEvent.CLIENT_POST.register(client -> {
            HudHandler.tick();
            KeyHandler.handleKeyInput();
        });

        ClientGuiEvent.RENDER_HUD.register((guiGraphics, tickDelta) -> {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.screen == null) {
                HudHandler.renderHud(guiGraphics,
                        mc.getWindow().getGuiScaledWidth(),
                        mc.getWindow().getGuiScaledHeight());
            }
        });

        ClientGuiEvent.RENDER_POST.register((screen, guiGraphics, mouseX, mouseY, tickDelta) -> {
            if (screen != null) {
                HudHandler.renderHud(guiGraphics, screen.width, screen.height);
            }
        });

        // populate trinket registry on client start — fixes "Disabled" tooltip on Fabric
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> UnlockManager.init());

        KeyBindingHelper.registerKeyBinding(KeyHandler.TRINKET_GUI);
        KeyBindingHelper.registerKeyBinding(KeyHandler.MAGNETO);

        EntityModelLayerRegistry.registerModelLayer(DarkVexModelLayer.DARK_VEX, DarkVexModel::createBodyLayer);
    }
}
