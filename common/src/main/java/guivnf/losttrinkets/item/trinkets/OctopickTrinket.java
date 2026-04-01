package guivnf.losttrinkets.item.trinkets;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

import java.util.Set;

public class OctopickTrinket extends Trinket<OctopickTrinket> {
    private static final ThreadLocal<ServerPlayer> octoMiningPlayer = new ThreadLocal<>();
    private static final TagKey<Block> ORE_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("neoforge", "ores"));
    private static final TagKey<Block> FABRIC_ORE_TAG = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("c", "ores"));

    public OctopickTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onBreak(Player player, BlockPos pos, BlockState state) {
        if (octoMiningPlayer.get() != null)
            return;
        if (!(player instanceof ServerPlayer serverPlayer))
            return;
        try {
            octoMiningPlayer.set(serverPlayer);
            mine(serverPlayer, pos, state);
        } finally {
            octoMiningPlayer.set(null);
        }
    }

    private static void mine(ServerPlayer player, BlockPos pos, BlockState state) {
        if (!player.hasCorrectToolForDrops(state))
            return;
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (!trinkets.isActive(Itms.OCTOPICK.get()))
            return;

        var level = player.serverLevel();
        Set<BlockPos> toBreak = Sets.newLinkedHashSet();
        if (state.is(ORE_TAG) || state.is(FABRIC_ORE_TAG) || state.is(Blocks.OBSIDIAN)) {
            toBreak.add(pos);
            for (BlockPos pos1 : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
                BlockPos imm1 = pos1.immutable();
                if (toBreak.contains(imm1))
                    continue;
                BlockState state1 = level.getBlockState(imm1);
                if (state.getBlock() == state1.getBlock()) {
                    toBreak.add(imm1);
                    for (BlockPos pos2 : BlockPos.betweenClosed(imm1.offset(-1, -1, -1), imm1.offset(1, 1, 1))) {
                        BlockPos imm2 = pos2.immutable();
                        if (toBreak.contains(imm2))
                            continue;
                        BlockState state2 = level.getBlockState(imm2);
                        if (state.getBlock() == state2.getBlock()) {
                            toBreak.add(imm2);
                        }
                    }
                }
            }
        }
        if (toBreak.size() > 1) {
            toBreak.forEach(breakPos -> {
                if (breakPos.equals(pos))
                    return;
                BlockState breakState = level.getBlockState(breakPos);
                if (player.hasCorrectToolForDrops(breakState)) {
                    if (player.gameMode.destroyBlock(breakPos)) {
                        level.levelEvent(2001, breakPos, Block.getId(breakState));
                    }
                }
            });
        }
    }

    public static boolean collectDrops(Entity entity) {
        ServerPlayer player = octoMiningPlayer.get();
        if (player == null)
            return false;
        if (entity.level() != player.level())
            return false;

        if (entity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getItem().copy();
            if (!stack.isEmpty()) {
                if (!player.getInventory().add(stack)) {

                    return false;
                }
            }
            return true;
        } else if (entity instanceof ExperienceOrb xpOrb) {
            player.giveExperiencePoints(xpOrb.getValue());
            return true;
        }
        return false;
    }
}
