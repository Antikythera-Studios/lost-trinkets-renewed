package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import guivnf.losttrinkets.handler.UnlockHandler;

@Mixin(MerchantResultSlot.class)
public class MerchantResultSlotMixin {
    @Inject(method = "onTake", at = @At("HEAD"))
    private void trade(Player player, ItemStack stack, CallbackInfo ci) {
        UnlockHandler.trade(player);
    }
}
