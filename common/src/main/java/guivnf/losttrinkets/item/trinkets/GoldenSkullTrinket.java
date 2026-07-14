package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.Itms;

public class GoldenSkullTrinket extends Trinket<GoldenSkullTrinket> {
    public GoldenSkullTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static ItemStack getExtraDrop(Player player, LivingEntity target) {
        if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.GOLDEN_SKULL.get())) {
            if (target instanceof Monster && target.level().getRandom().nextInt(20) == 0) {
                return new ItemStack(Itms.TREASURE_BAG.get());
            }
        }
        return ItemStack.EMPTY;
    }
}
