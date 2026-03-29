package guivnf.losttrinkets.item;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.registry.LTRegistry;
import net.minecraft.core.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;

public class ItemGroups {
    public static final LTRegistry<CreativeModeTab> REG = LTRegistry.create(Registries.CREATIVE_MODE_TAB,
            LostTrinkets.MOD_ID);
    public static final RegistrySupplier<CreativeModeTab> MAIN = REG.register("main",
            () -> CreativeTabRegistry.create(
                    builder -> builder
                            .title(Component.translatable("itemGroup." + LostTrinkets.MOD_ID))
                            .icon(() -> new ItemStack(Itms.CREEPO.get()))
                            .displayItems((params, output) -> {
                                Itms.ALL_ITEMS.forEach(s -> output.accept(s.get()));
                            })));
}
