package guivnf.losttrinkets.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class Texture {
    private final ResourceLocation location;
    private final int u;
    private final int v;
    private final int width;
    private final int height;
    private final int textureWidth;
    private final int textureHeight;

    public Texture(ResourceLocation location, int u, int v, int width, int height) {
        this(location, u, v, width, height, 256, 256);
    }

    public Texture(ResourceLocation location, int u, int v, int width, int height, int textureWidth,
            int textureHeight) {
        this.location = location;
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void draw(GuiGraphics graphics, int x, int y) {
        graphics.blit(location, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
