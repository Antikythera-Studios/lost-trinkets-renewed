package guivnf.losttrinkets.client.screen;

import net.minecraft.client.Minecraft;

public class Screens {
    public static void register() {
    }

    public static void checkScreenRefresh() {
        Minecraft mc = Minecraft.getInstance();
        // re open with fresh data to handle server corrections and post respawn syncs
        if (mc.screen instanceof TrinketsScreen) {
            mc.setScreen(new TrinketsScreen());
        }
    }
}
