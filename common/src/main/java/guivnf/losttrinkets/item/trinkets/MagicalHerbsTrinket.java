package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

import java.util.List;

public class MagicalHerbsTrinket extends Trinket<MagicalHerbsTrinket> {
    public MagicalHerbsTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static boolean shouldDenyEffect(Player player, Holder<MobEffect> effect) {
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (trinkets.isActive(Itms.MAGICAL_HERBS.get())) {
            return effect.value().getCategory() == MobEffectCategory.HARMFUL || effect == MobEffects.BAD_OMEN;
        }
        return false;
    }

    @Override
    public void onActivated(Level level, BlockPos pos, Player player) {
        if (level.isClientSide())
            return;
        List<Holder<MobEffect>> toRemove = player.getActiveEffectsMap().keySet().stream()
                .filter(e -> e.value().getCategory() == MobEffectCategory.HARMFUL || e == MobEffects.BAD_OMEN)
                .toList();
        toRemove.forEach(player::removeEffect);
    }
}
