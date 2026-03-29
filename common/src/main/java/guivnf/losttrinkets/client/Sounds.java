package guivnf.losttrinkets.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.registry.LTRegistry;

public class Sounds {
    public static final LTRegistry<SoundEvent> REG = LTRegistry.create(Registries.SOUND_EVENT, LostTrinkets.MOD_ID);
    public static final RegistrySupplier<SoundEvent> UNLOCK = register("unlock");

    static RegistrySupplier<SoundEvent> register(String name) {
        ResourceLocation rl = new ResourceLocation(LostTrinkets.MOD_ID, name);
        return REG.register(name, () -> SoundEvent.createVariableRangeEvent(rl));
    }
}
