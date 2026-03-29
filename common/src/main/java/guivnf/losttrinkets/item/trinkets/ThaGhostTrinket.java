package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.trinket.ITargetingTrinket;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class ThaGhostTrinket extends Trinket<ThaGhostTrinket> implements ITargetingTrinket, ITickableTrinket {
    public ThaGhostTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(Level level, BlockPos pos, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 2, 0, false, false, false));
    }

    @Override
    public boolean preventTargeting(Mob mob, Player player, boolean notAttacked) {
        return notAttacked && player.hasEffect(MobEffects.INVISIBILITY);
    }
}
