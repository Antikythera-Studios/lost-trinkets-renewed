package guivnf.losttrinkets.item.trinkets;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class BrokenTotemTrinket extends Trinket<BrokenTotemTrinket> {
    public BrokenTotemTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public boolean preventDeath(Player owner, DamageSource source) {
        if (owner.level().getRandom().nextInt(4) != 0)
            return false;
        if (owner instanceof ServerPlayer serverPlayer) {
            serverPlayer.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
            CriteriaTriggers.USED_TOTEM.trigger(serverPlayer, new ItemStack(Items.TOTEM_OF_UNDYING));
        }
        owner.setHealth(1.0F);
        owner.removeAllEffects();
        owner.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
        owner.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
        owner.level().broadcastEntityEvent(owner, (byte) 35);
        return true;
    }
}
