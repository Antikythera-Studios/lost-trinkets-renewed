package guivnf.losttrinkets.client.screen;

import net.minecraft.client.Minecraft;

public class Screens {
    public static void register() {
    }

    public static void checkScreenRefresh() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.screen instanceof TrinketsScreen) {
            mc.setScreen(new TrinketsScreen());
        }
    }
}
