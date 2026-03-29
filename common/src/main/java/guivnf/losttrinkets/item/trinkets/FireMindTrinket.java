package guivnf.losttrinkets.item.trinkets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.handler.TargetHandler;
import guivnf.losttrinkets.item.Itms;

public class FireMindTrinket extends Trinket<FireMindTrinket> {
    public FireMindTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void onLivingUpdate(LivingEntity entity) {
        if (entity instanceof Mob mob) {
            LivingEntity target = mob.getTarget();
            if (target == null) {
                target = TargetHandler.getBrainMemorySafe(mob.getBrain(), MemoryModuleType.ATTACK_TARGET).orElse(null);
            }
            if (target instanceof Player player) {
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
                if (trinkets.isActive(Itms.FIRE_MIND.get()) && !mob.fireImmune()) {
                    mob.setSecondsOnFire(3);
                }
            }
        }
    }
}
