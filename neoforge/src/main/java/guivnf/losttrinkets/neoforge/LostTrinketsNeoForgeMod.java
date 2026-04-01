package guivnf.losttrinkets.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.config.LTConfigs;

@Mod(LostTrinkets.MOD_ID)
public class LostTrinketsNeoForgeMod {

    public LostTrinketsNeoForgeMod(IEventBus modEventBus, ModContainer modContainer) {
        LostTrinkets.init();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(NeoForgeEventHandler::registerEntityAttributes);
        modEventBus.addListener(NeoForgeClientEventHandler::addPackFinders);
        modEventBus.addListener(NeoForgeClientEventHandler::registerKeyMappings);
        modEventBus.addListener(NeoForgeClientEventHandler::registerModelLayers);
        modEventBus.addListener(NeoForgeClientEventHandler::registerEntityRenderers);
        modEventBus.addListener((net.neoforged.fml.event.config.ModConfigEvent event) -> {
            if (event.getConfig().getSpec() == LTConfigs.SPEC) {
                LTConfigs.apply();
            }
        });
        modContainer.registerConfig(ModConfig.Type.COMMON, LTConfigs.SPEC);
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
