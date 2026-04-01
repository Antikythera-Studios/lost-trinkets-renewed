package guivnf.losttrinkets.neoforge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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

@EventBusSubscriber(modid = LostTrinkets.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class NeoForgeClientEventHandler {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        HudHandler.tick();
        KeyHandler.handleKeyInput();
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
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (Minecraft.getInstance().screen == null) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            Minecraft mc = Minecraft.getInstance();
            HudHandler.renderHud(guiGraphics,
                    mc.getWindow().getGuiScaledWidth(),
                    mc.getWindow().getGuiScaledHeight());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onScreenRender(ScreenEvent.Render.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        HudHandler.renderHud(guiGraphics, event.getScreen().width, event.getScreen().height);
    }

    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            var resourcePath = ModList.get()
                    .getModFileById(LostTrinkets.MOD_ID)
                    .getFile()
                    .findResource("resourcepacks/legacy");
            PackLocationInfo locationInfo = new PackLocationInfo(
                    "builtin/losttrinkets_legacy",
                    Component.translatable("pack.losttrinkets.legacy.title"),
                    PackSource.BUILT_IN,
                    Optional.empty()
            );
            Pack pack = Pack.readMetaAndCreate(
                    locationInfo,
                    new Pack.ResourcesSupplier() {
                        @Override
                        public PackResources openPrimary(PackLocationInfo info) {
                            return new PathPackResources(info, resourcePath);
                        }
                        @Override
                        public PackResources openFull(PackLocationInfo info, Pack.Metadata metadata) {
                            return new PathPackResources(info, resourcePath);
                        }
                    },
                    PackType.CLIENT_RESOURCES,
                    new PackSelectionConfig(false, Pack.Position.TOP, false)
            );
            if (pack != null) {
                event.addRepositorySource(consumer -> consumer.accept(pack));
            }
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
