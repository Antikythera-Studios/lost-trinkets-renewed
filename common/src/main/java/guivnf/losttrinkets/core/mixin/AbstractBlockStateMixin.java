package guivnf.losttrinkets.core.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import guivnf.losttrinkets.item.trinkets.DragonBreathTrinket;

import java.util.List;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class AbstractBlockStateMixin {

    @Inject(method = "getDrops", at = @At("TAIL"), cancellable = true)
    public void getDrops(LootParams.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        BlockState self = (BlockState) (Object) this;
        LootParams params = builder.withParameter(LootContextParams.BLOCK_STATE, self)
                .create(LootContextParamSets.BLOCK);
        List<ItemStack> drops = cir.getReturnValue();
        Entity entity = params.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof Player) {
            cir.setReturnValue(DragonBreathTrinket.autoSmelt(drops, (Player) entity));
        }
    }
}
