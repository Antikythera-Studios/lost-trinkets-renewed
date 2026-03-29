package guivnf.losttrinkets.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

import java.util.Optional;

public class MC {
    public static Optional<LocalPlayer> player() {
        Minecraft mc = Minecraft.getInstance();
        return Optional.ofNullable(mc.player);
    }

    public static Optional<ClientLevel> world() {
        Minecraft mc = Minecraft.getInstance();
        return Optional.ofNullable(mc.level);
    }
}
