package guivnf.losttrinkets.entity;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.registry.LTRegistry;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Entities {
        public static final LTRegistry<EntityType<?>> REG = LTRegistry.create(Registries.ENTITY_TYPE,
                        LostTrinkets.MOD_ID);

        public static final RegistrySupplier<EntityType<DarkVexEntity>> DARK_VEX = (RegistrySupplier<EntityType<DarkVexEntity>>) (RegistrySupplier<?>) REG
                        .register("dark_vex",
                                        () -> EntityType.Builder
                                                        .<DarkVexEntity>of(DarkVexEntity::new, MobCategory.CREATURE)
                                                        .sized(0.4F, 0.8F)
                                                        .clientTrackingRange(3)
                                                        .updateInterval(80)
                                                        .build("dark_vex"));
}
