package guivnf.losttrinkets.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class Texture {
    private final Identifier location;
    private final int u;
    private final int v;
    private final int width;
    private final int height;
    private final int textureWidth;
    private final int textureHeight;

    public Texture(Identifier location, int u, int v, int width, int height) {
        this(location, u, v, width, height, 256, 256);
    }

    public Texture(Identifier location, int u, int v, int width, int height, int textureWidth,
            int textureHeight) {
        this.location = location;
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void draw(GuiGraphicsExtractor graphics, int x, int y) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, location, x, y, (float) u, (float) v,
                width, height, textureWidth, textureHeight);
    }

    public Identifier getLocation() {
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
