package guivnf.losttrinkets.item.trinkets;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
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

import java.util.List;

public class TrebleHooksTrinket extends Trinket<TrebleHooksTrinket> {
    public TrebleHooksTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onFished(Player player, FishingHook hook) {
        if (!(player.level() instanceof ServerLevel serverLevel))
            return;
        if (!LostTrinketsAPI.getTrinkets(player).isActive(Itms.TREBLE_HOOKS.get()))
            return;

        Entity hookedEntity = hook.getHookedIn(); // may be null for normal fishing
        ItemStack rod = player.getMainHandItem();
        LootTable lootTable = serverLevel.getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);

        for (int i = 0; i < 2; i++) {
            LootParams.Builder builder = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, hook.position())
                    .withParameter(LootContextParams.TOOL, rod)
                    .withParameter(LootContextParams.THIS_ENTITY, hook)
                    .withLuck(player.getLuck());
            if (hookedEntity != null) {
                builder.withParameter(LootContextParams.KILLER_ENTITY, hookedEntity);
            }
            List<ItemStack> list = lootTable.getRandomItems(builder.create(LootContextParamSets.FISHING));
            for (ItemStack stack : list) {
                ItemEntity itemEntity = new ItemEntity(serverLevel,
                        hook.getX(), hook.getY(), hook.getZ(), stack);
                double d0 = player.getX() - hook.getX();
                double d1 = player.getY() - hook.getY();
                double d2 = player.getZ() - hook.getZ();
                itemEntity.setDeltaMovement(d0 * 0.1D,
                        d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D,
                        d2 * 0.1D);
                serverLevel.addFreshEntity(itemEntity);
            }
        }
    }
}
