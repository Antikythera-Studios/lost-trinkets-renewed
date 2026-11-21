package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class GoldenHorseshoeTrinket extends Trinket<GoldenHorseshoeTrinket> {
    public GoldenHorseshoeTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public int getLootingBonus(Player owner) {
        return 1;
    }
}
