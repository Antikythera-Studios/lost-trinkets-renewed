package guivnf.losttrinkets.item.trinkets;

import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

public class OctopusLegTrinket extends Trinket<OctopusLegTrinket> {
    public OctopusLegTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onAttack(LivingEntity entityLiving, DamageSource source) {
        if (!(entityLiving.level() instanceof ServerLevel serverLevel))
            return;
        if (source == null)
            return;
        Entity immediateSource = source.getDirectEntity();
        if (entityLiving instanceof Player player) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (immediateSource instanceof LivingEntity living) {
                if (trinkets.isActive(Itms.OCTOPUS_LEG.get())) {
                    var server = serverLevel.getServer();
                    server.schedule(new TickTask(server.getTickCount() + 1, () -> disarm(serverLevel, living)));
                }
            }
        }
    }

    private static void disarm(ServerLevel level, LivingEntity living) {
        if (!living.isAlive())
            return;
        ItemStack stack = living.getMainHandItem();
        if (!stack.isEmpty() && level.getRandom().nextInt(5) == 0) {
            ItemStack stack1 = stack.copy();
            if (stack1.isDamageableItem()) {
                if (!stack1.isDamaged()) {
                    int damage = stack1.getMaxDamage();
                    if (damage > 10) {
                        damage /= 2;
                        damage = 10 + level.getRandom().nextInt(damage);
                    }
                    stack1.setDamageValue(damage);
                }
            }
            living.spawnAtLocation(level, stack1);
            living.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        }
    }
}
