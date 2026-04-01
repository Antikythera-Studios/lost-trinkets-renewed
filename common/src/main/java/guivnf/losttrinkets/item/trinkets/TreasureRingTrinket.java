package guivnf.losttrinkets.item.trinkets;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.Itms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreasureRingTrinket extends Trinket<TreasureRingTrinket> {
    public static final List<ResourceKey<LootTable>> LOOTS = Arrays.asList(
            BuiltInLootTables.NETHER_BRIDGE,
            BuiltInLootTables.JUNGLE_TEMPLE,
            BuiltInLootTables.BURIED_TREASURE,
            BuiltInLootTables.END_CITY_TREASURE,
            BuiltInLootTables.ABANDONED_MINESHAFT,
            BuiltInLootTables.DESERT_PYRAMID,
            BuiltInLootTables.SIMPLE_DUNGEON,
            BuiltInLootTables.STRONGHOLD_LIBRARY,
            BuiltInLootTables.STRONGHOLD_CORRIDOR,
            BuiltInLootTables.STRONGHOLD_CROSSING,
            BuiltInLootTables.VILLAGE_WEAPONSMITH);

    public TreasureRingTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    private static boolean isBossMob(LivingEntity entity) {
        return entity instanceof EnderDragon || entity instanceof WitherBoss;
    }

    public static List<ItemStack> getExtraDrops(Player player, LivingEntity target) {
        if (!LostTrinketsAPI.getTrinkets(player).isActive(Itms.TREASURE_RING.get()))
            return Collections.emptyList();
        if (!isBossMob(target))
            return Collections.emptyList();
        if (!(player.level() instanceof ServerLevel serverLevel))
            return Collections.emptyList();

        ResourceKey<LootTable> lootTableId = LOOTS.get(serverLevel.random.nextInt(LOOTS.size()));
        LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(lootTableId);
        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, target.position())
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withLuck(player.getLuck())
                .create(LootContextParamSets.CHEST);
        return lootTable.getRandomItems(params);
    }
}
