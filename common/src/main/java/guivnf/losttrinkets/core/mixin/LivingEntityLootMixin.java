package guivnf.losttrinkets.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import guivnf.losttrinkets.handler.EventHandler;

import java.util.function.Consumer;

// Fabric has no LivingDropsEvent (that's a NeoForge patch), so the Golden Tooth / Golden Horseshoe
// looting bonus is applied here by wrapping the loot consumer. NeoForge handles it in
// NeoForgeEventHandler#onLivingDrops instead, so this is guarded to Fabric to avoid double drops.
@Mixin(LivingEntity.class)
public class LivingEntityLootMixin {
    @WrapOperation(method = "dropFromLootTable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;ZLnet/minecraft/resources/ResourceKey;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V"))
    private void losttrinkets$fabricLooting(LootTable table, LootParams params, long seed, Consumer<ItemStack> consumer,
            Operation<Void> original, @Local(argsOnly = true) DamageSource source) {
        Consumer<ItemStack> out = consumer;
        if (dev.architectury.platform.Platform.isFabric() && source.getEntity() instanceof Player player) {
            int looting = EventHandler.getLootingLevel(player);
            if (looting > 0) {
                RandomSource rand = ((LivingEntity) (Object) this).getRandom();
                out = stack -> {
                    consumer.accept(stack);
                    int extra = rand.nextInt(looting + 1);
                    if (extra > 0) {
                        ItemStack copy = stack.copy();
                        copy.setCount(extra);
                        consumer.accept(copy);
                    }
                };
            }
        }
        original.call(table, params, seed, out);
    }
}
