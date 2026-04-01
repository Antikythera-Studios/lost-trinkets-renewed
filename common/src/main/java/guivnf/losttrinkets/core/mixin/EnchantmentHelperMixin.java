package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "getMobLooting", at = @At("RETURN"), cancellable = true)
    private static void losttrinkets$lootingBonus(LivingEntity entity,
            CallbackInfoReturnable<Integer> cir) {
        if (dev.architectury.platform.Platform.isNeoForge())
            return;
        if (!(entity instanceof Player player))
            return;
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        int bonus = 0;
        if (trinkets.isActive(Itms.GOLDEN_HORSESHOE.get()))
            bonus++;
        if (trinkets.isActive(Itms.GOLDEN_TOOTH.get()))
            bonus++;
        if (bonus > 0)
            cir.setReturnValue(cir.getReturnValueI() + bonus);
    }
}
