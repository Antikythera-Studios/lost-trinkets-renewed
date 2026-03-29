package guivnf.losttrinkets.forge;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.client.handler.ClientEventHandler;
import guivnf.losttrinkets.client.handler.KeyHandler;
import guivnf.losttrinkets.client.handler.hud.HudHandler;
import guivnf.losttrinkets.client.model.DarkVexModel;
import guivnf.losttrinkets.client.render.entity.DarkVexModelLayer;

@Mod.EventBusSubscriber(modid = LostTrinkets.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEventHandler {

    @SubscribeEvent
    public static void onClientTick(net.minecraftforge.event.TickEvent.ClientTickEvent event) {
        if (event.phase == net.minecraftforge.event.TickEvent.Phase.END) {
            HudHandler.tick();
            KeyHandler.handleKeyInput();
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void onRenderLivingPre(RenderLivingEvent.Pre<?, ?> event) {
        LivingEntity living = event.getEntity();
        LivingEntityRenderer renderer = event.getRenderer();
        boolean cancel = ClientEventHandler.onRenderLivingPre(
                living, renderer, event.getPoseStack(), event.getMultiBufferSource(),
                event.getPartialTick(), event.getPackedLight());
        if (cancel) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        if (net.minecraft.client.Minecraft.getInstance().screen == null) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            HudHandler.renderHud(guiGraphics,
                    event.getWindow().getGuiScaledWidth(),
                    event.getWindow().getGuiScaledHeight());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onScreenRender(ScreenEvent.Render.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        HudHandler.renderHud(guiGraphics, event.getScreen().width, event.getScreen().height);
    }

    // called from mod event bus
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            var resourcePath = ModList.get()
                    .getModFileById(LostTrinkets.MOD_ID)
                    .getFile()
                    .findResource("resourcepacks/legacy");
            var pack = Pack.readMetaAndCreate(
                    "builtin/losttrinkets_legacy",
                    Component.translatable("pack.losttrinkets.legacy.title"),
                    false,
                    (name) -> new PathPackResources(name, resourcePath, true),
                    PackType.CLIENT_RESOURCES,
                    Pack.Position.TOP,
                    PackSource.BUILT_IN
            );
            if (pack != null) {
                event.addRepositorySource(consumer -> consumer.accept(pack));
            }
        }
    }

    // called from mod event bus
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyHandler.TRINKET_GUI);
        event.register(KeyHandler.MAGNETO);
    }

    // called from mod event bus
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DarkVexModelLayer.DARK_VEX, DarkVexModel::createBodyLayer);
    }

    // called from mod event bus — must happen at EntityRenderersEvent time, before FMLClientSetupEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(guivnf.losttrinkets.entity.Entities.DARK_VEX.get(),
                guivnf.losttrinkets.client.render.entity.DarkVexRenderer::new);
    }
}
