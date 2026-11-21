package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RubyHeartTrinket extends Trinket<RubyHeartTrinket> {
    /// Each player's health right before their last hit.
    private static final Map<UUID, Float> LAST_HEALTH = new HashMap<>();

    public RubyHeartTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void onHurt(Player owner, DamageSource source, float amount) {
        LAST_HEALTH.put(owner.getUUID(), owner.getHealth());
    }

    @Override
    public boolean preventDeath(Player owner, DamageSource source) {
        if (LAST_HEALTH.getOrDefault(owner.getUUID(), owner.getHealth()) > 6.0F) {
            owner.setHealth(1.0F);
            return true;
        }
        return false;
    }
}
