package guivnf.losttrinkets.api;

import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.api.trinket.Trinkets;

import java.util.Set;

public interface ILostTrinketsAPI {

    boolean unlock(Player player, ITrinket trinket);

    void unlock(Player player);

    Trinkets getTrinkets(Player player);

    PlayerData getData(Player player);

    Set<ITrinket> getTrinkets();

    default boolean isEnabled(ITrinket trinket) {
        return getTrinkets().contains(trinket);
    }

    default boolean isDisabled(ITrinket trinket) {
        return !isEnabled(trinket);
    }

    // only contains trinkets that are also enabled
    Set<ITrinket> getRandomTrinkets();

    default boolean isRandom(ITrinket trinket) {
        return getRandomTrinkets().contains(trinket);
    }

    default boolean isNonRandom(ITrinket trinket) {
        return !isRandom(trinket);
    }
}
