package guivnf.losttrinkets.util;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import guivnf.losttrinkets.core.mixin.ItemEntityAccessor;

public class Magnet {
    public static boolean canCollectManual(ItemEntity entity) {
        int delay = ((ItemEntityAccessor) entity).getPickupDelay();
        return delay != Short.MAX_VALUE;
    }

    public static boolean canCollectManualOrb(ExperienceOrb orb) {
        return orb.tickCount >= 0;
    }
}
