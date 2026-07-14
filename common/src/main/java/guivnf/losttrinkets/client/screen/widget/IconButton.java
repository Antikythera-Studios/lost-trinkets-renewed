package guivnf.losttrinkets.client.screen.widget;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import guivnf.losttrinkets.client.screen.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IconButton extends Button {
    private final Texture texture;
    private Consumer<List<Component>> tooltipProvider;

    public IconButton(int x, int y, Texture texture, OnPress onPress, Screen parent) {
        super(x, y, texture.getWidth(), texture.getHeight(),
                Component.empty(), onPress, DEFAULT_NARRATION);
        this.texture = texture;
    }

    public IconButton setTooltip(Consumer<List<Component>> provider) {
        this.tooltipProvider = provider;
        return this;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        texture.draw(graphics, getX(), getY());
        if (isHovered() && tooltipProvider != null) {
            List<Component> list = new ArrayList<>();
            tooltipProvider.accept(list);
            if (!list.isEmpty()) {
                graphics.setComponentTooltipForNextFrame(
                        net.minecraft.client.Minecraft.getInstance().font,
                        list, mouseX, mouseY);
            }
        }
    }
}
