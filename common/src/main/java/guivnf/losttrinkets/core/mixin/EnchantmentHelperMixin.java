package guivnf.losttrinkets.core.mixin;

import dev.architectury.platform.Platform;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.handler.EventHandler;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    // Fabric only, Forge uses LootingLevelEvent
    @Inject(method = "getMobLooting", at = @At("RETURN"), cancellable = true)
    private static void losttrinkets$lootingBonus(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (Platform.isForge() || !(entity instanceof Player player))
            return;
        int bonus = EventHandler.getLootingBonus(player);
        if (bonus > 0) {
            cir.setReturnValue(cir.getReturnValueI() + bonus);
        }
    }
}
