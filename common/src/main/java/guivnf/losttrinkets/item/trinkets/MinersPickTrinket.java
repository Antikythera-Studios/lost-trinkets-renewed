package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.Itms;

public class MinersPickTrinket extends Trinket<MinersPickTrinket> {
    public MinersPickTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onBreakSpeed(Player player, float original) {
        if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.MINERS_PICK.get())) {
            return original + 3.7F;
        }
        return original;
    }
}
