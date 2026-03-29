package guivnf.losttrinkets.api.trinket;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface ITargetingTrinket extends ITrinket {
    /**
     * checks if the given mob is prevented from targeting the player.
     * boss mobs are not prevented.
     *
     * @param mob         mob that is trying to target a player
     * @param player      player that has this trinket activated
     * @param notAttacked if the mob has not been attacked by the player
     */
    boolean preventTargeting(Mob mob, Player player, boolean notAttacked);
}
