package guivnf.losttrinkets.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.neoforged.fml.config.ModConfig;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.config.LTConfigs;
import guivnf.losttrinkets.entity.DarkVexEntity;
import guivnf.losttrinkets.entity.Entities;

public class LostTrinketsFabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        LostTrinkets.init();
        LostTrinkets.setup();
        FabricDefaultAttributeRegistry.register(Entities.DARK_VEX.get(), DarkVexEntity.createAttributes().build());
        NeoForgeModConfigEvents.loading(LostTrinkets.MOD_ID).register(event -> LTConfigs.apply());
        NeoForgeModConfigEvents.reloading(LostTrinkets.MOD_ID).register(event -> LTConfigs.apply());
        NeoForgeConfigRegistry.INSTANCE.register(LostTrinkets.MOD_ID, ModConfig.Type.COMMON, LTConfigs.SPEC);
        FabricEventHandler.register();
    }
}
