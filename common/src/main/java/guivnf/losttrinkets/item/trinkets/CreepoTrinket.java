package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.Itms;

public class CreepoTrinket extends Trinket<CreepoTrinket> {
    public CreepoTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onCriticalHit(Player player, Entity target) {
        if (LostTrinketsAPI.getTrinkets(player).isActive(Itms.CREEPO.get())) {
            if (target instanceof Creeper creeper) {
                creeper.setSwellDir(-1);
            }
        }
    }
}
