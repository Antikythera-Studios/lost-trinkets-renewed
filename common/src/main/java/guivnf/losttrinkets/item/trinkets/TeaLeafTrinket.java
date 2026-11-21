package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class TeaLeafTrinket extends Trinket<TeaLeafTrinket> {
    public TeaLeafTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public boolean preventEffect(Player owner, Holder<MobEffect> effect) {
        return effect == MobEffects.WITHER || effect == MobEffects.POISON;
    }

    @Override
    public void onActivated(Level level, BlockPos pos, Player player) {
        if (level.isClientSide())
            return;
        player.removeEffect(MobEffects.POISON);
        player.removeEffect(MobEffects.WITHER);
    }
}
