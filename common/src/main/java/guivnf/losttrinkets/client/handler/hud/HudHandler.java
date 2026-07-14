package guivnf.losttrinkets.client.handler.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import guivnf.losttrinkets.client.screen.Textures;
import guivnf.losttrinkets.util.Ticker;

import org.jetbrains.annotations.Nullable;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HudHandler {
    private static final List<Toast> TOASTS = new ArrayList<>();
    private static final Ticker ticker = new Ticker(60);

    @Nullable
    private static Toast toast;

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null && !TOASTS.isEmpty()) {
            TOASTS.clear();
            toast = null;
        }
        Iterator<Toast> itr = TOASTS.iterator();
        while (itr.hasNext()) {
            Toast b = itr.next();
            if (!b.getTicker().ended()) {
                toast = b;
                if (ticker.ended()) {
                    b.getTicker().onward();
                }
                ticker.add(5);
            } else {
                ticker.back(5);
                if (ticker.getTicks() <= 0) {
                    toast = null;
                    itr.remove();
                }
            }
            if (toast != null)
                break;
        }
        if (TOASTS.isEmpty()) {
            toast = null;
        }
    }

    public static void renderHud(GuiGraphicsExtractor guiGraphics, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (toast != null) {
            int x = width / 2 - Textures.TOAST.getWidth() / 2;
            int y = (int) (4 - 60.0F + ticker.getTicks());
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(x, y);
            Textures.TOAST.draw(guiGraphics, 0, 0);
            guiGraphics.text(mc.font,
                    Component.translatable("gui.losttrinkets.trinket.unlocked"),
                    41, 10, new Color(0xFFBA6F).getRGB(), false);
            String s = Component.translatable(toast.getTrinket().getItem().getDescriptionId()).getString();
            s = StringUtils.abbreviate(s, 20);
            guiGraphics.text(mc.font, s, 41, 23, 0xFFF0C6E5, false);
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(5.0F, 5.0F);
            guiGraphics.pose().scale(2.0F, 2.0F);
            guiGraphics.fakeItem(new ItemStack(toast.getTrinket().getItem()), 0, 0);
            guiGraphics.pose().popMatrix();
            guiGraphics.pose().popMatrix();
        }
    }

    public static void add(Toast toast) {
        TOASTS.add(toast);
    }
}
