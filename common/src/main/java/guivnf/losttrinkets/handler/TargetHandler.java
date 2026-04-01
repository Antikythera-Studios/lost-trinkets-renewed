package guivnf.losttrinkets.handler;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.LostTrinketsAPI;

import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import java.util.UUID;

public class TargetHandler {

    public static boolean preventTargeting(LivingEntity attacker, @Nullable LivingEntity target) {
        if (attacker instanceof Mob) {
            Mob mob = (Mob) attacker;
            if (target instanceof Player) {
                Player player = (Player) target;

                if (isBossMob(mob)) {
                    return false;
                }

                boolean notAttacked = !player.equals(mob.getLastHurtByMob());

                return LostTrinketsAPI.getTrinkets(player).getTargeting().stream()
                        .anyMatch(trinket -> trinket.preventTargeting(mob, player, notAttacked));
            }
        }
        return false;
    }

    private static boolean isBossMob(Mob mob) {
        return mob instanceof EnderDragon || mob instanceof WitherBoss;
    }

    public static <T> Optional<T> getBrainMemorySafe(Brain<?> brain, MemoryModuleType<T> type) {
        return brain.hasMemoryValue(type) ? brain.getMemory(type) : Optional.empty();
    }

    public static void setTarget(LivingEntity living, @Nullable LivingEntity target) {
        if (living instanceof Mob && preventTargeting(living, target)) {
            ((Mob) living).setTarget(null);
        }
    }

    public static void onLivingUpdate(LivingEntity living) {
        if (living instanceof Mob) {
            Mob mob = (Mob) living;

            if (mob instanceof NeutralMob) {
                NeutralMob angerable = (NeutralMob) mob;
                UUID targetUUID = angerable.getPersistentAngerTarget();
                if (targetUUID != null) {
                    Player targetPlayer = (Player) mob.level().getPlayerByUUID(targetUUID);
                    if (preventTargeting(mob, targetPlayer)) {
                        angerable.stopBeingAngry();
                    }
                }
            }

            if (preventTargeting(mob, mob.getTarget())) {
                mob.setTarget(null);
            }

            Brain<?> brain = mob.getBrain();
            getBrainMemorySafe(brain, MemoryModuleType.ATTACK_TARGET).ifPresent(target -> {
                if (preventTargeting(mob, target)) {
                    brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
                }
            });
        }
    }
}
