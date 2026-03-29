package guivnf.losttrinkets.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.item.Itms;

@Mixin(WebBlock.class)
public class CobwebBlockMixin {
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void losttrinkets$glassShard(BlockState state, Level level, BlockPos pos,
            Entity entity, CallbackInfo ci) {
        if (!level.isClientSide() && entity instanceof Player player) {
            if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.GLASS_SHARDS.get())) {
                level.destroyBlock(pos, false, player);
                ci.cancel();
            }
        }
    }
}
