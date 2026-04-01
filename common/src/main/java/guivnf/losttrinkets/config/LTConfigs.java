package guivnf.losttrinkets.config;

import com.google.common.collect.Lists;
import net.neoforged.neoforge.common.ModConfigSpec;

public class LTConfigs {
        private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        private static final ModConfigSpec.IntValue START_SLOTS;
        private static final ModConfigSpec.IntValue MAX_SLOTS;
        private static final ModConfigSpec.IntValue SLOT_COST;
        private static final ModConfigSpec.IntValue SLOT_UP_FACTOR;

        private static final ModConfigSpec.BooleanValue UNLOCK_ENABLED;
        private static final ModConfigSpec.LongValue UNLOCK_COOLDOWN;
        private static final ModConfigSpec.ConfigValue<java.util.List<? extends String>> BLACK_LIST;
        private static final ModConfigSpec.ConfigValue<java.util.List<? extends String>> NON_RANDOM;

        private static final ModConfigSpec.BooleanValue KILLING_UNLOCK_ENABLED;
        private static final ModConfigSpec.IntValue KILLING;

        private static final ModConfigSpec.BooleanValue BOSS_KILLING_UNLOCK_ENABLED;
        private static final ModConfigSpec.IntValue BOSS_KILLING;

        private static final ModConfigSpec.BooleanValue FARMING_UNLOCK_ENABLED;
        private static final ModConfigSpec.IntValue FARMING;

        private static final ModConfigSpec.BooleanValue ORES_MINING_UNLOCK_ENABLED;
        private static final ModConfigSpec.IntValue ORES_MINING;

        private static final ModConfigSpec.BooleanValue TRADING_UNLOCK_ENABLED;
        private static final ModConfigSpec.IntValue TRADING;

        private static final ModConfigSpec.BooleanValue WOOD_CUTTING_UNLOCK_ENABLED;
        private static final ModConfigSpec.IntValue WOOD_CUTTING;

        public static final ModConfigSpec SPEC;

        static {
                BUILDER.push("Trinket_Slots");
                START_SLOTS = BUILDER.comment(
                                "Numbers of trinket slots the player will start with (Only effect newer players!!).")
                                .defineInRange("startSlots", 1, 0, 40);
                MAX_SLOTS = BUILDER.comment(
                                "Maximum number of trinket slots the player can have (does not remove unlocked slots)")
                                .defineInRange("maxSlots", 40, 1, 40);
                SLOT_COST = BUILDER.comment("Levels of xp needed to unlock a trinket slot.").defineInRange("slotCost",
                                15, 0, 1000);
                SLOT_UP_FACTOR = BUILDER.comment("Amount of Xp levels added to the next unlocking cost.")
                                .defineInRange("slotUpFactor", 3, 0, 1000);
                BUILDER.pop();

                UNLOCK_ENABLED = BUILDER.comment("Set to false to disable the default way of unlocking trinkets.")
                                .define("unlockEnabled", true);
                UNLOCK_COOLDOWN = BUILDER.comment("Cooldown (ticks) between unlocks").defineInRange("unlockCooldown",
                                2400L, 0L, 1728000L);
                BLACK_LIST = BUILDER
                                .comment("List of banned trinkets eg: [\"losttrinkets:piggy\", \"losttrinkets:magical_feathers\"]",
                                                "The trinkets listed in here will also be removed from players that already unlocked them.")
                                .defineList("blackList", Lists.newArrayList(), e -> e instanceof String);
                NON_RANDOM = BUILDER
                                .comment("List of trinkets that can't be unlocked randomly eg: [\"losttrinkets:piggy\", \"losttrinkets:magical_feathers\"]",
                                                "The trinkets listed in here will not be removed from players that already unlocked them.")
                                .defineList("nonRandom", Lists.newArrayList(), e -> e instanceof String);

                BUILDER.push("Killing_Unlocks");
                KILLING_UNLOCK_ENABLED = BUILDER
                                .comment("Set to false to disable unlocking trinkets from killing non-Boss entities.")
                                .define("killingUnlockEnabled", true);
                KILLING = BUILDER.comment(
                                "Rarity of unlocking a trinket from killing non-Boss entities. (Greater number = more rare)")
                                .defineInRange("killing", 120, 2, 100000);
                BUILDER.pop();

                BUILDER.push("Bosses_Killing_Unlocks");
                BOSS_KILLING_UNLOCK_ENABLED = BUILDER
                                .comment("Set to false to disable unlocking trinkets from killing Bosses.")
                                .define("bossKillingUnlockEnabled", true);
                BOSS_KILLING = BUILDER.comment(
                                "Rarity of unlocking a trinket from killing Bosses. (Greater number = more rare)")
                                .defineInRange("bossKilling", 10, 2, 100000);
                BUILDER.pop();

                BUILDER.push("Farming_Unlocks");
                FARMING_UNLOCK_ENABLED = BUILDER.comment("Set to false to disable unlocking trinkets from farming.")
                                .define("farmingUnlockEnabled", true);
                FARMING = BUILDER.comment("Rarity of unlocking a trinket from farming. (Greater number = more rare)")
                                .defineInRange("farming", 140, 2, 100000);
                BUILDER.pop();

                BUILDER.push("Ores_Mining_Unlocks");
                ORES_MINING_UNLOCK_ENABLED = BUILDER
                                .comment("Set to false to disable unlocking trinkets from mining ores.")
                                .define("oresMiningUnlockEnabled", true);
                ORES_MINING = BUILDER
                                .comment("Rarity of unlocking a trinket from mining ores. (Greater number = more rare)")
                                .defineInRange("oresMining", 100, 2, 100000);
                BUILDER.pop();

                BUILDER.push("Trading_Unlocks");
                TRADING_UNLOCK_ENABLED = BUILDER.comment("Set to false to disable unlocking trinkets from trading.")
                                .define("tradingUnlockEnabled", true);
                TRADING = BUILDER.comment("Rarity of unlocking a trinket from trading. (Greater number = more rare)")
                                .defineInRange("trading", 30, 2, 100000);
                BUILDER.pop();

                BUILDER.push("Wood_Cutting_Unlocks");
                WOOD_CUTTING_UNLOCK_ENABLED = BUILDER
                                .comment("Set to false to disable unlocking trinkets from cutting trees.")
                                .define("woodCuttingUnlockEnabled", true);
                WOOD_CUTTING = BUILDER.comment(
                                "Rarity of unlocking a trinket from cutting trees. (Greater number = more rare)")
                                .defineInRange("woodCutting", 170, 2, 100000);
                BUILDER.pop();

                SPEC = BUILDER.build();
        }

        public static void apply() {
                Configs.GENERAL.startSlots = START_SLOTS.get();
                Configs.GENERAL.maxSlots = MAX_SLOTS.get();
                Configs.GENERAL.slotCost = SLOT_COST.get();
                Configs.GENERAL.slotUpFactor = SLOT_UP_FACTOR.get();

                Configs.GENERAL.unlockEnabled = UNLOCK_ENABLED.get();
                Configs.GENERAL.unlockCooldown = UNLOCK_COOLDOWN.get();
                Configs.GENERAL.blackList = new java.util.ArrayList<>(BLACK_LIST.get());
                Configs.GENERAL.nonRandom = new java.util.ArrayList<>(NON_RANDOM.get());

                Configs.GENERAL.killingUnlockEnabled = KILLING_UNLOCK_ENABLED.get();
                Configs.GENERAL.killing = KILLING.get();

                Configs.GENERAL.bossKillingUnlockEnabled = BOSS_KILLING_UNLOCK_ENABLED.get();
                Configs.GENERAL.bossKilling = BOSS_KILLING.get();

                Configs.GENERAL.farmingUnlockEnabled = FARMING_UNLOCK_ENABLED.get();
                Configs.GENERAL.farming = FARMING.get();

                Configs.GENERAL.oresMiningUnlockEnabled = ORES_MINING_UNLOCK_ENABLED.get();
                Configs.GENERAL.oresMining = ORES_MINING.get();

                Configs.GENERAL.tradingUnlockEnabled = TRADING_UNLOCK_ENABLED.get();
                Configs.GENERAL.trading = TRADING.get();

                Configs.GENERAL.woodCuttingUnlockEnabled = WOOD_CUTTING_UNLOCK_ENABLED.get();
                Configs.GENERAL.woodCutting = WOOD_CUTTING.get();

                Configs.GENERAL.refresh();
        }
}
