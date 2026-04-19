package guivnf.losttrinkets.client.handler;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.HitResult;
import guivnf.losttrinkets.client.screen.TrinketsScreen;
import guivnf.losttrinkets.client.util.MC;
import guivnf.losttrinkets.item.trinkets.MagnetoTrinket;

public class KeyHandler {
    public static final String TRINKET_CATEGORY = "key.categories.losttrinkets";
    private static boolean prevUseKeyDown = false;
    public static final KeyMapping TRINKET_GUI = new KeyMapping(
            "key.losttrinkets.trinket",
            InputConstants.Type.KEYSYM,
            org.lwjgl.glfw.GLFW.GLFW_KEY_R,
            TRINKET_CATEGORY);
    public static final KeyMapping MAGNETO = new KeyMapping(
            "key.losttrinkets.magneto",
            InputConstants.UNKNOWN.getValue(),
            TRINKET_CATEGORY);

    private static boolean isMagnetoUnbound() {
        Component keyMsg = MAGNETO.getTranslatedKeyMessage();
        return keyMsg.getContents() instanceof net.minecraft.network.chat.contents.TranslatableContents tc
                && tc.getKey().equals("key.keyboard.unknown");
    }

    /**
     * returns the tooltip hint for the maagneto trinket based on current key
     * binding
     */
    public static Component getMagnetoHint() {
        if (isMagnetoUnbound()) {
            return Component.translatable("info.losttrinkets.magneto.unbound").withStyle(ChatFormatting.GRAY);
        }
        return Component.translatable("info.losttrinkets.magneto.bound", MAGNETO.getTranslatedKeyMessage()).withStyle(ChatFormatting.GRAY);
    }

    /** called each client tick to handle key presses.. */
    public static void handleKeyInput() {
        if (TRINKET_GUI.consumeClick()) {
            Minecraft.getInstance().setScreen(new TrinketsScreen());
        }
        if (MAGNETO.consumeClick()) {
            MC.player().ifPresent(MagnetoTrinket::trySendCollect);
        }

        // Fallback when magneto key is unbound: right-click with empty main hand in air.
        // The vanilla client never sends ServerboundUseItemPacket for empty-hand MISS,
        // so server-side events don't fire — we must detect it client-side and send the packet.
        Minecraft mc = Minecraft.getInstance();
        boolean useKeyDown = mc.options.keyUse.isDown();
        if (useKeyDown && !prevUseKeyDown
                && isMagnetoUnbound()
                && mc.screen == null
                && mc.player != null
                && mc.player.getMainHandItem().isEmpty()
                && mc.hitResult != null
                && mc.hitResult.getType() == HitResult.Type.MISS) {
            MagnetoTrinket.trySendCollect(mc.player);
        }
        prevUseKeyDown = useKeyDown;
    }
}
