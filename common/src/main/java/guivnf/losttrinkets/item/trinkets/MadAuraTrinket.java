package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class MadAuraTrinket extends Trinket<MadAuraTrinket> {
    public MadAuraTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public boolean onAttacked(Player owner, DamageSource source) {
        return source.getDirectEntity() instanceof AbstractArrow;
    }
}
