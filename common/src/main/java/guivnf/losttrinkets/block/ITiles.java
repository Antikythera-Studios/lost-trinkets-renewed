package guivnf.losttrinkets.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.registry.LTRegistry;

public class ITiles {
    public static final LTRegistry<BlockEntityType<?>> REG = LTRegistry.create(Registries.BLOCK_ENTITY_TYPE,
            LostTrinkets.MOD_ID);
}
