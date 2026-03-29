package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class StarfishTrinket extends Trinket<StarfishTrinket> {
    public StarfishTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onHurt(LivingEntity entityLiving, DamageSource source, float amount) {
        if (!(entityLiving instanceof Monster)) return amount;
        Entity entity = source.getDirectEntity();
        if (entity instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.STARFISH.get())) {
                player.giveExperiencePoints(Math.max(1, (int) (amount / 2.0F)));
            }
        }
        return amount;
    }
}
