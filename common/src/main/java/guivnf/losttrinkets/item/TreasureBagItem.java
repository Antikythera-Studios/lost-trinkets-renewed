package guivnf.losttrinkets.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;
import java.util.Objects;

public class TreasureBagItem extends Item {
    public TreasureBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level instanceof ServerLevel serverLevel) {
            LootParams lootParams = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .withLuck(player.getLuck())
                    .create(LootContextParamSets.GIFT);
            ResourceLocation rl = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(this));
            LootTable lootTable = serverLevel.getServer().getLootData().getLootTable(rl);
            List<ItemStack> stacks = lootTable.getRandomItems(lootParams);
            stacks.forEach(stack -> {
                if (!player.getInventory().add(stack.copy())) {
                    player.drop(stack.copy(), false);
                }
            });
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
