package guivnf.losttrinkets.item.trinkets;

import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

// handled entirely by PlayerEntityMixin#losttrinkets$horseshoeStepHeight
public class HorseshoeTrinket extends Trinket<HorseshoeTrinket> {
    public HorseshoeTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }
}
