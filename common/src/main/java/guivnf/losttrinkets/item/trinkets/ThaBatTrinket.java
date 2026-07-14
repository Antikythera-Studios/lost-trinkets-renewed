package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class ThaBatTrinket extends Trinket<ThaBatTrinket> implements ITickableTrinket {
    public ThaBatTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(Level level, BlockPos pos, Player player) {
        if (!level.isClientSide() && player.tickCount % 90 == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500, 0, false, false));
        }
    }
}
