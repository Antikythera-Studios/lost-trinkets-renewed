package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

    @Override
    public void onAttack(Player owner, LivingEntity target, DamageSource source, float amount) {
        if (target instanceof Creeper creeper && source.getDirectEntity() == owner) {
            creeper.setSwellDir(-1);
        }
    }

    /// Returns true if the exploder is a creeper targeting a player with Creepo active.
    public static boolean preventExplosion(Entity exploder) {
        return exploder instanceof Creeper creeper
                && creeper.getTarget() instanceof Player player
                && LostTrinketsAPI.getTrinkets(player).isActive(Itms.CREEPO.get());
    }
}
