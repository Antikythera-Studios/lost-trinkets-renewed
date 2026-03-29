package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class ThaSpiderTrinket extends Trinket<ThaSpiderTrinket> {
    public ThaSpiderTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static boolean doClimb(LivingEntity entity) {
        if (entity instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.THA_SPIDER.get())) {
                return entity.horizontalCollision;
            }
        }
        return false;
    }
}
