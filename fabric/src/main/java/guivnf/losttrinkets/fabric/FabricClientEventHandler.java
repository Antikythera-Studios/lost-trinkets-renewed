package guivnf.losttrinkets.fabric;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.world.InteractionHand;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
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

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> UnlockManager.init());

        InteractionEvent.CLIENT_RIGHT_CLICK_AIR.register((player, hand) -> {
            if (hand == InteractionHand.MAIN_HAND && player.getMainHandItem().isEmpty()) {
                guivnf.losttrinkets.item.trinkets.MagnetoTrinket.trySendCollect(player);
            }
        });

        KeyMappingRegistry.register(KeyHandler.TRINKET_GUI);
        KeyMappingRegistry.register(KeyHandler.MAGNETO);

        EntityModelLayerRegistry.register(DarkVexModelLayer.DARK_VEX, DarkVexModel::createBodyLayer);
    }
}
