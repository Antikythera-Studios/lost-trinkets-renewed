package guivnf.losttrinkets.api.trinket;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface ITargetingTrinket extends ITrinket {
    /// Returns true to stop the mob from targeting the player. Bosses are never stopped.
    ///
    /// @param notAttacked true if the player hasn't attacked the mob
    boolean preventTargeting(Mob mob, Player player, boolean notAttacked);
}
