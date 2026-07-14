package guivnf.losttrinkets.item.trinkets;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

import java.util.HashMap;
import java.util.UUID;

public class RubyHeartTrinket extends Trinket<RubyHeartTrinket> {

    private static final HashMap<UUID, Float> lastHealths = new HashMap<>();

    public RubyHeartTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void saveHealth(Player player) {
        lastHealths.put(player.getUUID(), player.getHealth());
    }

    public static boolean onDeath(LivingEntity entityLiving, DamageSource source) {
        if (entityLiving instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            boolean flag = false;
            if (trinkets.isActive(Itms.RUBY_HEART.get())) {
                float savedHealth = lastHealths.getOrDefault(player.getUUID(), player.getHealth());
                if (savedHealth > 6.0F) {
                    player.setHealth(1.0F);
                    flag = true;
                }
            }
            if (!flag && trinkets.isActive(Itms.BROKEN_TOTEM.get())) {
                if (player.level().getRandom().nextInt(4) == 0) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
                        CriteriaTriggers.USED_TOTEM.trigger(serverPlayer, new ItemStack(Items.TOTEM_OF_UNDYING));
                    }
                    player.setHealth(1.0F);
                    player.removeAllEffects();
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                    player.level().broadcastEntityEvent(player, (byte) 35);
                    flag = true;
                }
            }
            return flag;
        }
        return false;
    }
}
