package guivnf.losttrinkets.client.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.client.screen.widget.TrinketButton;
import guivnf.losttrinkets.client.screen.widget.IconButton;
import guivnf.losttrinkets.network.packet.SetActivePacket;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AvailableTrinketsScreen extends AbstractLTScreen {
    @Nullable
    protected final Screen prevScreen;
    private int x, y, headID;
    private final int columns = 14, rows = 5, btnDim = 28;
    private final List<TrinketButton> trinketButtons = new ArrayList<>();

    public AvailableTrinketsScreen(@Nullable Screen prevScreen, int headID) {
        super(Component.translatable("gui.losttrinkets.trinket.available"));
        this.prevScreen = prevScreen;
        this.headID = headID;
    }

    @Override
    protected void init() {
        trinketButtons.clear();
        if (this.minecraft.player != null) {
            this.x = this.width / 2 - this.columns * this.btnDim / 2;
            this.y = this.height / 2 - this.rows * this.btnDim / 2;
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(this.minecraft.player);
            List<ITrinket> all = trinkets.getAvailableTrinkets();
            int total = all.size();
            int cur = 0;
            for (int j1 = 0; j1 < this.rows; ++j1) {
                for (int j2 = 0; j2 < this.columns; ++j2) {
                    int i = j2 + j1 * this.columns + this.headID;
                    if (i + 1 <= total) {
                        final ITrinket trinket = all.get(i);
                        final int idx = i;
                        TrinketButton tb = new TrinketButton(
                                this.x + j2 * this.btnDim, this.y + j1 * this.btnDim,
                                Textures.TRINKET_BG, trinket,
                                button -> {
                                    LostTrinkets.NET.toServer(new SetActivePacket(idx));
                                    trinkets.setActive(trinket, this.minecraft.player);
                                    this.minecraft.setScreen(new TrinketsScreen());
                                });
                        trinketButtons.add(tb);
                        addRenderableWidget(tb);
                        cur++;
                    }
                }
            }
            int x1 = this.x + this.columns * this.btnDim / 2 - this.btnDim / 2 - 30;
            int y1 = this.y + 150;
            int i = this.columns * this.rows;
            if (cur == this.columns * this.rows && total > this.headID) {
                addRenderableWidget(new IconButton(60 + x1, y1, Textures.TRINKET_NEXT,
                        button -> this.minecraft
                                .setScreen(new AvailableTrinketsScreen(this.prevScreen, this.headID + i)),
                        this));
            }
            if (this.headID > 0) {
                addRenderableWidget(new IconButton(x1, y1, Textures.TRINKET_PREV,
                        button -> this.minecraft
                                .setScreen(new AvailableTrinketsScreen(this.prevScreen, Math.max(0, this.headID - i))),
                        this));
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mx, int my, float pt) {
        renderBackground(guiGraphics);
        if (this.minecraft.player != null) {
            List<ITrinket> all = LostTrinketsAPI.getTrinkets(this.minecraft.player).getAvailableTrinkets();
            if (all.isEmpty()) {
                String name = Component.translatable("gui.losttrinkets.trinket.empty").getString();
                guiGraphics.drawString(this.font, name, this.width / 2 - this.font.width(name) / 2, this.height / 2 - 5,
                        0x999999, false);
            }
        }
        super.render(guiGraphics, mx, my, pt);
        String s = getTitle().getString();
        guiGraphics.drawString(this.font, s, this.width / 2 - this.font.width(s) / 2, this.y - 20, 0x999999);
        for (TrinketButton btn : this.trinketButtons) {
            if (btn.isHovered()) {
                ITrinket trinket = btn.trinket;
                List<Component> list = new ArrayList<>();
                list.add(new ItemStack(trinket.getItem()).getHoverName());
                trinket.addTrinketDescription(new ItemStack(trinket.getItem()), list);
                list.add(Component
                        .translatable(
                                "gui.losttrinkets.rarity." + trinket.getRarity().name().toLowerCase(Locale.ENGLISH))
                        .withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
                guiGraphics.renderComponentTooltip(this.font, list, mx, my);
                break;
            }
        }
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
