package guivnf.losttrinkets.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
        modEventBus.addListener(ForgeEventHandler::registerEntityAttributes);
        modEventBus.addListener((net.minecraftforge.fml.event.config.ModConfigEvent event) -> {
            if (event.getConfig().getSpec() == guivnf.losttrinkets.config.LTConfigs.SPEC) {
                guivnf.losttrinkets.config.LTConfigs.apply();
            }
        });
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ForgeClientMod.init(modEventBus));

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, guivnf.losttrinkets.config.LTConfigs.SPEC);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(LostTrinkets::setup);
    }
}
