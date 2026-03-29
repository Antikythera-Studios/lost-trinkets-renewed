package guivnf.losttrinkets.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.config.Configs;
import guivnf.losttrinkets.impl.LostTrinketsAPIImpl;
import guivnf.losttrinkets.util.Ticker;

import java.util.*;

public class UnlockHandler {

    private static final TagKey<Block> FORGE_ORES = TagKey.create(Registries.BLOCK,
            new ResourceLocation("forge", "ores"));
    private static final TagKey<Block> COMMON_ORES = TagKey.create(Registries.BLOCK,
            new ResourceLocation("c", "ores"));
    private static final TagKey<Block> COMMON_LOGS = TagKey.create(Registries.BLOCK,
            new ResourceLocation("c", "logs"));

    private static final Map<UUID, Type> MAP = new HashMap<>();
    private static final Ticker DELAY = new Ticker(10);
    private static boolean flag;

    public static void tick(Player player) {
        List<ITrinket> trinkets = LostTrinketsAPIImpl.UNLOCK_QUEUE.get(player.getUUID());
        if (trinkets != null) {
            trinkets.forEach(trinket -> UnlockManager.unlock(player, trinket, false));
        }
        LostTrinketsAPIImpl.UNLOCK_QUEUE.remove(player.getUUID());

        Iterator<UUID> itr = LostTrinketsAPIImpl.WEIGHTED_UNLOCK_QUEUE.iterator();
        while (itr.hasNext()) {
            if (itr.next().equals(player.getUUID())) {
                UnlockManager.unlock(player, false);
                itr.remove();
            }
        }

        checkUnlocks(player);
    }

    private static void checkUnlocks(Player player) {
        if (Configs.GENERAL.unlockEnabled) {
            UUID id = player.getUUID();
            if (DELAY.isEmpty() && MAP.containsKey(id)) {
                if (player.level().getRandom().nextInt(MAP.get(id).getRandom()) == 0) {
                    UnlockManager.unlock(player, true);
                }
                flag = true;
            }
            if (flag) {
                DELAY.onward();
                MAP.remove(id);
                if (DELAY.ended()) {
                    DELAY.reset();
                    flag = false;
                }
            }
        }
    }

    public static void queueUnlock(Player player, Type type) {
        if (!player.level().isClientSide) {
            MAP.put(player.getUUID(), type);
        }
    }

    public static void trade(Player player) {
        if (Configs.GENERAL.unlockEnabled && Configs.GENERAL.tradingUnlockEnabled) {
            if (!player.level().isClientSide) {
                queueUnlock(player, Type.TRADING);
            }
        }
    }

    public static void kill(Player player, boolean isBoss) {
        if (Configs.GENERAL.unlockEnabled && !player.level().isClientSide) {
            if (isBoss) {
                if (Configs.GENERAL.bossKillingUnlockEnabled) {
                    queueUnlock(player, Type.BOSS_KILL);
                }
            } else {
                if (Configs.GENERAL.killingUnlockEnabled) {
                    queueUnlock(player, Type.KILL);
                }
            }
        }
    }

    public static void checkBlockHarvest(Player player, Level level, BlockPos pos, BlockState state) {
        if (Configs.GENERAL.unlockEnabled && !level.isClientSide) {
            if (state.is(FORGE_ORES) || state.is(COMMON_ORES)) {
                if (Configs.GENERAL.oresMiningUnlockEnabled) {
                    queueUnlock(player, Type.ORE_MINE);
                }
            } else if (state.is(BlockTags.CROPS)) {
                if (Configs.GENERAL.farmingUnlockEnabled) {
                    queueUnlock(player, Type.FARM_HARVEST);
                }
            } else if (state.is(BlockTags.LOGS) || state.is(COMMON_LOGS)) {
                if (Configs.GENERAL.woodCuttingUnlockEnabled) {
                    queueUnlock(player, Type.WOOD_CUTTING);
                }
            }
        }
    }

    public static void useHoe(Player player) {
        if (Configs.GENERAL.unlockEnabled && Configs.GENERAL.farmingUnlockEnabled) {
            if (!player.level().isClientSide) {
                queueUnlock(player, Type.FARM_HARVEST);
            }
        }
    }

    public static void bonemeal(Player player) {
        if (Configs.GENERAL.unlockEnabled && Configs.GENERAL.farmingUnlockEnabled) {
            if (!player.level().isClientSide) {
                queueUnlock(player, Type.FARM_HARVEST);
            }
        }
    }

    public enum Type {
        KILL, BOSS_KILL, ORE_MINE, WOOD_CUTTING, FARM_HARVEST, TRADING;

        public int getRandom() {
            switch (this) {
                case KILL:
                    return Configs.GENERAL.killing;
                case BOSS_KILL:
                    return Configs.GENERAL.bossKilling;
                case ORE_MINE:
                    return Configs.GENERAL.oresMining;
                case TRADING:
                    return Configs.GENERAL.trading;
                case FARM_HARVEST:
                    return Configs.GENERAL.farming;
                case WOOD_CUTTING:
                    return Configs.GENERAL.woodCutting;
                default:
                    return Integer.MAX_VALUE;
            }
        }
    }
}
