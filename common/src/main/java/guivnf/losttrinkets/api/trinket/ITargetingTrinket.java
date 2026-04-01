package guivnf.losttrinkets.api.trinket;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public interface ITargetingTrinket extends ITrinket {

    boolean preventTargeting(Mob mob, Player player, boolean notAttacked);
}
