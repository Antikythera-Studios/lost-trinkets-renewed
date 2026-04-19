package guivnf.losttrinkets.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.client.network.ClientPackets;
import guivnf.losttrinkets.client.render.entity.EntityRenderer;
import guivnf.losttrinkets.client.render.tile.TileRenderer;
import guivnf.losttrinkets.client.screen.Screens;

public class LostTrinketsFabricClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.registerBuiltinResourcePack(
                new ResourceLocation(LostTrinkets.MOD_ID, "legacy"),
                FabricLoader.getInstance().getModContainer(LostTrinkets.MOD_ID).orElseThrow(),
                ResourcePackActivationType.NORMAL
        );
        ClientPackets.register();
        EntityRenderer.register();
        TileRenderer.register();
        Screens.register();
        FabricClientEventHandler.register();
    }
}
