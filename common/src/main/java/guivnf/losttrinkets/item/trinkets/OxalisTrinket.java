package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class OxalisTrinket extends Trinket<OxalisTrinket> {
    public OxalisTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static boolean shouldDenyEffect(Player player, MobEffect effect) {
        Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
        if (trinkets.isActive(Itms.OXALIS.get())) {
            return effect == MobEffects.BAD_OMEN || effect == MobEffects.UNLUCK;
        }
        return false;
    }

    @Override
    public void onActivated(Level level, BlockPos pos, Player player) {
        if (level.isClientSide)
            return;
        player.removeEffect(MobEffects.BAD_OMEN);
        player.removeEffect(MobEffects.UNLUCK);
    }
}
