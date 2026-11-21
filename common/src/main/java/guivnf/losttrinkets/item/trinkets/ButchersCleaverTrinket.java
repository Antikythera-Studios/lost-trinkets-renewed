package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

import java.util.List;

public class ButchersCleaverTrinket extends Trinket<ButchersCleaverTrinket> {
    public ButchersCleaverTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void addKillDrops(Player owner, LivingEntity target, List<ItemStack> drops) {
        if (target instanceof Animal && target.level().random.nextInt(10) == 0) {
            drops.add(new ItemStack(Items.BONE, target.level().random.nextInt(2) + 1));
        }
    }
}
