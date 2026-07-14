package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.Itms;

public class ButchersCleaverTrinket extends Trinket<ButchersCleaverTrinket> {
    public ButchersCleaverTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static ItemStack getExtraDrop(Player player, LivingEntity target) {
        if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.BUTCHERS_CLEAVER.get())) {
            if (target instanceof Animal && target.level().getRandom().nextInt(10) == 0) {
                return new ItemStack(Items.BONE, target.level().getRandom().nextInt(2) + 1);
            }
        }
        return ItemStack.EMPTY;
    }
}
