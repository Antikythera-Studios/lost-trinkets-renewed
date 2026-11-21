package guivnf.losttrinkets.item.trinkets;

import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;

public class OctopusLegTrinket extends Trinket<OctopusLegTrinket> {
    public OctopusLegTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public boolean onAttacked(Player owner, DamageSource source) {
        if (owner.level() instanceof ServerLevel serverLevel && source.getDirectEntity() instanceof LivingEntity living) {
            var server = serverLevel.getServer();
            server.schedule(new TickTask(server.getTickCount() + 1, () -> disarm(serverLevel, living)));
        }
        return false;
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
