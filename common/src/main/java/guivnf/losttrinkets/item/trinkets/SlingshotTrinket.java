package guivnf.losttrinkets.item.trinkets;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class SlingshotTrinket extends Trinket<SlingshotTrinket> {
    public SlingshotTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static float onHurt(LivingEntity entityLiving, DamageSource source, float amount) {
        Entity entity = source.getDirectEntity();
        if (entity instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.SLINGSHOT.get())) {
                entityLiving.knockback(1.4,
                        Mth.sin(player.getYRot() * (float) (Math.PI / 180F)),
                        -Mth.cos(player.getYRot() * (float) (Math.PI / 180F)));
            }
        }
        return amount;
    }
}
