package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class TeaLeafTrinket extends Trinket<TeaLeafTrinket> {
    public TeaLeafTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static boolean shouldDenyEffect(Player player, Holder<MobEffect> effect) {
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (trinkets.isActive(Itms.TEA_LEAF.get())) {
            return effect == MobEffects.WITHER || effect == MobEffects.POISON;
        }
        return false;
    }

    @Override
    public void onActivated(Level level, BlockPos pos, Player player) {
        if (level.isClientSide) return;
        player.removeEffect(MobEffects.POISON);
        player.removeEffect(MobEffects.WITHER);
    }
}
