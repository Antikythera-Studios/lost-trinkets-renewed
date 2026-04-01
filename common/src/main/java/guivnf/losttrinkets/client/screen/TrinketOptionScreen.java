package guivnf.losttrinkets.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.network.packet.SetInactivePacket;

import org.jetbrains.annotations.Nullable;

public class TrinketOptionScreen extends AbstractLTScreen {
    private final ITrinket trinket;

    @Nullable
    protected final Screen prevScreen;

    protected TrinketOptionScreen(ITrinket trinket, @Nullable Screen prevScreen) {
        super(Component.translatable(trinket.getItem().getDescriptionId()));
        this.trinket = trinket;
        this.prevScreen = prevScreen;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 30;
        int y = this.height / 3 + 50;
        if (this.minecraft.player != null) {
            addRenderableWidget(Button.builder(Component.translatable("gui.losttrinkets.remove"), btn -> {
                Trinkets trinkets = LostTrinketsAPI.getTrinkets(this.minecraft.player);
                int i = trinkets.getActiveTrinkets().indexOf(this.trinket);
                if (i >= 0) {
                    LostTrinkets.NET.toServer(new SetInactivePacket(i));
                    trinkets.setInactive(this.trinket, this.minecraft.player);
                    this.minecraft.setScreen(new TrinketsScreen());
                }
            }).bounds(x, y, 60, 20).build());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mx, int my, float pt) {
        renderBackground(guiGraphics, mx, my, pt);
        int x = this.width / 2 - 8;
        int y = this.height / 3 - 8;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x - 16.0F, y - 16.0F, 0.0F);
        guiGraphics.pose().scale(3.0F, 3.0F, 1.0F);
        guiGraphics.renderFakeItem(new ItemStack(this.trinket.getItem()), 0, 0);
        guiGraphics.pose().popPose();
        super.render(guiGraphics, mx, my, pt);
        String name = Component.translatable(this.trinket.getItem().getDescriptionId()).getString();
        guiGraphics.drawString(this.font, name, 8 + x - this.font.width(name) / 2, y + 32, 0x999999, false);
    }

    @Override
    public void onClose() {
        if (this.prevScreen instanceof TrinketsScreen) {
            this.minecraft.setScreen(this.prevScreen);
        } else {
            super.onClose();
        }
    }
}
