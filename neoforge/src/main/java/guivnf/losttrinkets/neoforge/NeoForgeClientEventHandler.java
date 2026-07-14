package guivnf.losttrinkets.neoforge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;

import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.client.handler.ClientEventHandler;
import guivnf.losttrinkets.client.handler.KeyHandler;
import guivnf.losttrinkets.client.handler.hud.HudHandler;
import guivnf.losttrinkets.client.model.DarkVexModel;
import guivnf.losttrinkets.client.render.entity.DarkVexModelLayer;

@EventBusSubscriber(modid = LostTrinkets.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientEventHandler {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        HudHandler.tick();
        KeyHandler.handleKeyInput();
    }

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void onRenderLivingPre(RenderLivingEvent.Pre<?, ?, ?> event) {
        boolean cancel = ClientEventHandler.onRenderLivingPre(
                event.getRenderState(), event.getRenderer(), event.getPoseStack(), event.getPartialTick());
        if (cancel) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (Minecraft.getInstance().screen == null) {
            GuiGraphicsExtractor guiGraphics = event.getGuiGraphics();
            Minecraft mc = Minecraft.getInstance();
            HudHandler.renderHud(guiGraphics,
                    mc.getWindow().getGuiScaledWidth(),
                    mc.getWindow().getGuiScaledHeight());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onScreenRender(ScreenEvent.Render.Post event) {
        GuiGraphicsExtractor guiGraphics = event.getGuiGraphics();
        HudHandler.renderHud(guiGraphics, event.getScreen().width, event.getScreen().height);
    }

    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            // MC 26.1: AddPackFindersEvent#addPackFinders locates the pack from the mod's resources
            // by Identifier, replacing the old ModFile#findResource + PathPackResources plumbing.
            event.addPackFinders(
                    net.minecraft.resources.Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, "resourcepacks/legacy"),
                    PackType.CLIENT_RESOURCES,
                    Component.translatable("pack.losttrinkets.legacy.title"),
                    PackSource.BUILT_IN,
                    false,
                    Pack.Position.TOP);
        }
    }

    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyHandler.TRINKET_GUI);
        event.register(KeyHandler.MAGNETO);
    }

    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DarkVexModelLayer.DARK_VEX, DarkVexModel::createBodyLayer);
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(guivnf.losttrinkets.entity.Entities.DARK_VEX.get(),
                guivnf.losttrinkets.client.render.entity.DarkVexRenderer::new);
    }
}
