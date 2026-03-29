package guivnf.losttrinkets.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import guivnf.losttrinkets.LostTrinkets;

@Mod(LostTrinkets.MOD_ID)
public class LostTrinketsForgeMod {
    public LostTrinketsForgeMod() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // must register before calling LostTrinkets.init() so DeferredRegister can find the event bus
        EventBuses.registerModEventBus(LostTrinkets.MOD_ID, modEventBus);

        LostTrinkets.init();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(ForgeEventHandler::registerEntityAttributes);
        modEventBus.addListener(ForgeClientEventHandler::addPackFinders);
        modEventBus.addListener(ForgeClientEventHandler::registerKeyMappings);
        modEventBus.addListener(ForgeClientEventHandler::registerModelLayers);
        modEventBus.addListener(ForgeClientEventHandler::registerEntityRenderers);
        modEventBus.addListener((net.minecraftforge.fml.event.config.ModConfigEvent event) -> {
            if (event.getConfig().getSpec() == guivnf.losttrinkets.config.LTConfigs.SPEC) {
                guivnf.losttrinkets.config.LTConfigs.apply();
            }
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, guivnf.losttrinkets.config.LTConfigs.SPEC);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(LostTrinkets::setup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            guivnf.losttrinkets.client.render.entity.EntityRenderer.register();
            guivnf.losttrinkets.client.render.tile.TileRenderer.register();
            guivnf.losttrinkets.client.screen.Screens.register();
        });
    }
}
