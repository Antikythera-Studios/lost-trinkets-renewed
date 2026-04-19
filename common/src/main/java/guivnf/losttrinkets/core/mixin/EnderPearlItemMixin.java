package guivnf.losttrinkets.core.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.item.Itms;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EnderpearlItem.class, priority = 500)
public abstract class EnderPearlItemMixin {
    @WrapWithCondition(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V"))
    private boolean losttrinkets$emptyAmulet(ItemStack instance, int i, LivingEntity livingEntity, @Local(argsOnly = true) Player player) {
        return !LostTrinketsAPI.getTrinkets(player).isActive(Itms.EMPTY_AMULET.get());
    }
}
