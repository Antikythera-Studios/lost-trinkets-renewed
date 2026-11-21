package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class FireMindTrinket extends Trinket<FireMindTrinket> {
    public FireMindTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onTargeted(Player owner, Mob mob) {
        if (!mob.fireImmune()) {
            mob.setSecondsOnFire(3);
        }
    }
}
