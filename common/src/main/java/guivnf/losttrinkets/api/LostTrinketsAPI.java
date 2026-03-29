package guivnf.losttrinkets.api;

import net.minecraft.world.entity.player.Player;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.api.trinket.Trinkets;

import org.jetbrains.annotations.Nullable;
import java.util.Objects;

public class LostTrinketsAPI {

    @Nullable
    private static ILostTrinketsAPI instance = null;

    public static void init(ILostTrinketsAPI impl) {
        LostTrinketsAPI.instance = impl;
    }

    public static ILostTrinketsAPI get() {
        return Objects.requireNonNull(instance);
    }

    public static Trinkets getTrinkets(Player player) {
        return get().getTrinkets(player);
    }

    public static PlayerData getData(Player player) {
        return get().getData(player);
    }
}
