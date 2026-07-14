package guivnf.losttrinkets.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;
import guivnf.losttrinkets.client.handler.KeyHandler;

import org.jetbrains.annotations.Nullable;

public class AbstractLTScreen extends LTScreen {
    private boolean refresh;
    @Nullable
    private AbstractLTScreen toRefresh;

    protected AbstractLTScreen(Component title) {
        super(title);
    }

    @Override
    public void tick() {
        if (this.refresh && this.toRefresh != null) {
            this.minecraft.setScreen(this.toRefresh);
            this.toRefresh = null;
        }

        this.refresh = false;
    }

    public void refresh() {
        this.refresh = true;
    }

    public void setRefreshScreen(@Nullable AbstractLTScreen screen) {
        this.toRefresh = screen;
    }

    @Override
    public boolean keyPressed(net.minecraft.client.input.KeyEvent keyEvent) {
        if (super.keyPressed(keyEvent)) {
            return true;
        } else {
            if (KeyHandler.TRINKET_GUI.matches(keyEvent)) {
                if (this.minecraft.player != null) {
                    this.onClose();
                }
                return true;
            }
        }
        return false;
    }
}
