package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.item.Itms;

import org.jetbrains.annotations.Nullable;

@Mixin(EnchantmentMenu.class)
public class EnchantmentContainerMixin {
    @Shadow
    public int[] costs;

    @Nullable
    private Player ltPlayer;

    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At("RETURN"))
    private void ltInit(int id, Inventory inv, ContainerLevelAccess access, CallbackInfo ci) {
        this.ltPlayer = inv.player;
    }

    @Inject(method = "slotsChanged", at = @At("TAIL"))
    private void ltSlotsChanged(Container container, CallbackInfo ci) {
        if (ltPlayer != null && LostTrinketsAPI.getTrinkets(ltPlayer).isActive(Itms.BOOK_O_ENCHANTING.get())) {
            for (int i = 0; i < costs.length; i++) {
                if (costs[i] > 0)
                    costs[i] = 30;
            }
        }
    }
}
