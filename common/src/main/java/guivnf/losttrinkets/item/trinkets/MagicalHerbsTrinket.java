package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

import java.util.List;

public class MagicalHerbsTrinket extends Trinket<MagicalHerbsTrinket> {
    public MagicalHerbsTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public boolean preventEffect(Player owner, Holder<MobEffect> effect) {
        return effect.value().getCategory() == MobEffectCategory.HARMFUL || effect == MobEffects.BAD_OMEN;
    }

    @Override
    public void onActivated(Level level, BlockPos pos, Player player) {
        if (level.isClientSide)
            return;
        List<Holder<MobEffect>> toRemove = player.getActiveEffectsMap().keySet().stream()
                .filter(effect -> preventEffect(player, effect))
                .toList();
        toRemove.forEach(player::removeEffect);
    }
}
