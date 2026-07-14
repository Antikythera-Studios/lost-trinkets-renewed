package guivnf.losttrinkets.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class StackHelper {
    public static void drop(Entity entity, ItemStack stack) {
        if (entity.level().isClientSide())
            return;
        ItemEntity itemEntity = new ItemEntity(
                entity.level(),
                entity.getX(), entity.getY(), entity.getZ(),
                stack);
        itemEntity.setDefaultPickUpDelay();
        entity.level().addFreshEntity(itemEntity);
    }

    public static void giveToPlayer(net.minecraft.world.entity.player.Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            ItemEntity entity = player.drop(stack, false);
            if (entity != null) {
                entity.setNoPickUpDelay();
                entity.setThrower(player);
            }
        }
    }
}
