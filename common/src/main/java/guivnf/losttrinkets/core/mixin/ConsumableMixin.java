package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.handler.EventHandler;

@Mixin(Consumable.class)
public class ConsumableMixin {
    // HEAD, the stack is already shrunk by TAIL
    @Inject(method = "onConsume", at = @At("HEAD"))
    private void losttrinkets$onItemEaten(Level level, LivingEntity entity, ItemStack stack,
            CallbackInfoReturnable<ItemStack> cir) {
        if (!level.isClientSide() && entity instanceof Player player) {
            EventHandler.onItemEaten(player, stack);
        }
    }
}
