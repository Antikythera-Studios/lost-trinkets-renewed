package guivnf.losttrinkets.client.handler;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import guivnf.losttrinkets.client.screen.TrinketsScreen;
import guivnf.losttrinkets.client.util.MC;
import guivnf.losttrinkets.item.trinkets.MagnetoTrinket;

public class KeyHandler {
    public static final KeyMapping.Category TRINKET_CATEGORY = new KeyMapping.Category(
            net.minecraft.resources.Identifier.fromNamespaceAndPath(guivnf.losttrinkets.LostTrinkets.MOD_ID, "trinkets"));
    public static final KeyMapping TRINKET_GUI = new KeyMapping(
            "key.losttrinkets.trinket",
            InputConstants.Type.KEYSYM,
            org.lwjgl.glfw.GLFW.GLFW_KEY_R,
            TRINKET_CATEGORY);
    public static final KeyMapping MAGNETO = new KeyMapping(
            "key.losttrinkets.magneto",
            InputConstants.UNKNOWN.getValue(),
            TRINKET_CATEGORY);

    public static Component getMagnetoHint() {
        Component keyMsg = MAGNETO.getTranslatedKeyMessage();

        boolean unbound = keyMsg.getContents() instanceof net.minecraft.network.chat.contents.TranslatableContents tc
                && tc.getKey().equals("key.keyboard.unknown");
        if (unbound) {
            return Component.translatable("info.losttrinkets.magneto.unbound").withStyle(ChatFormatting.GRAY);
        }
        return Component.translatable("info.losttrinkets.magneto.bound", keyMsg).withStyle(ChatFormatting.GRAY);
    }

    public static void handleKeyInput() {
        if (TRINKET_GUI.consumeClick()) {
            Minecraft.getInstance().setScreen(new TrinketsScreen());
        }
        if (MAGNETO.consumeClick()) {
            MC.player().ifPresent(MagnetoTrinket::trySendCollect);
        }
    }
}
