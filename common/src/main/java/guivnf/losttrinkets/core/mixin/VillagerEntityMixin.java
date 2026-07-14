package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

@Mixin(Villager.class)
public class VillagerEntityMixin {
    @Inject(method = "getPlayerReputation", at = @At("TAIL"), cancellable = true)
    public void getPlayerReputation(Player player, CallbackInfoReturnable<Integer> cir) {
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (trinkets.isActive(Itms.KARMA.get())) {
            cir.setReturnValue(cir.getReturnValueI() + 100);
        }
    }
}
