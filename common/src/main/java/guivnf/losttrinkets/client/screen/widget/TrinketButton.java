package guivnf.losttrinkets.client.screen.widget;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.client.screen.Texture;

public class TrinketButton extends Button {
    public final ITrinket trinket;
    private final Texture texture;

    public TrinketButton(int x, int y, Texture texture, ITrinket trinket, OnPress pressable) {
        super(x, y, texture.getWidth(), texture.getHeight(), Component.empty(), pressable, DEFAULT_NARRATION);
        this.trinket = trinket;
        this.texture = texture;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.visible) {
            this.isHovered = mouseX >= this.getX() && mouseY >= this.getY()
                    && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
            this.texture.draw(guiGraphics, this.getX(), this.getY());
            int i = (this.texture.getWidth() - 16) / 2;
            int j = (this.texture.getHeight() - 16) / 2;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(i + this.getX() - 2.0F, j + this.getY() - 2.0F);
            guiGraphics.pose().scale(1.25F, 1.25F);
            guiGraphics.fakeItem(new ItemStack(this.trinket.getItem()), 0, 0);
            guiGraphics.pose().popMatrix();
        }
    }
}
