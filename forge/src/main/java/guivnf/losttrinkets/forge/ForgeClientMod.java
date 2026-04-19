package guivnf.losttrinkets.forge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import guivnf.losttrinkets.client.network.ClientPackets;
import guivnf.losttrinkets.client.render.entity.EntityRenderer;
import guivnf.losttrinkets.client.render.tile.TileRenderer;
import guivnf.losttrinkets.client.screen.Screens;

public class ForgeClientMod {

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(ForgeClientMod::clientSetup);
        modEventBus.addListener(ForgeClientEventHandler::addPackFinders);
        modEventBus.addListener(ForgeClientEventHandler::registerKeyMappings);
        modEventBus.addListener(ForgeClientEventHandler::registerModelLayers);
        modEventBus.addListener(ForgeClientEventHandler::registerEntityRenderers);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ClientPackets.register();
            EntityRenderer.register();
            TileRenderer.register();
            Screens.register();
        });
    }
}
