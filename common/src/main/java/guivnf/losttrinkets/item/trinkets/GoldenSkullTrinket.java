package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.Itms;

import java.util.List;

public class GoldenSkullTrinket extends Trinket<GoldenSkullTrinket> {
    public GoldenSkullTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void addKillDrops(Player owner, LivingEntity target, List<ItemStack> drops) {
        if (target instanceof Monster && target.level().random.nextInt(20) == 0) {
            drops.add(new ItemStack(Itms.TREASURE_BAG.get()));
        }
    }
}
