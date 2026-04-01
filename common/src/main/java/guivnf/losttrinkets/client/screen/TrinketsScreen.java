package guivnf.losttrinkets.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.client.screen.widget.TrinketButton;
import guivnf.losttrinkets.config.Configs;
import guivnf.losttrinkets.network.packet.UnlockSlotPacket;
import guivnf.losttrinkets.client.screen.widget.IconButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrinketsScreen extends AbstractLTScreen {
    private int x, y;
    private final int columns = 10, rows = 4, btnDim = 28;
    private final List<TrinketButton> trinketButtons = new ArrayList<>();

    public TrinketsScreen() {
        super(Component.translatable("gui.losttrinkets.trinket.active"));
    }

    @Override
    protected void init() {
        trinketButtons.clear();
        if (this.minecraft.player != null) {
            this.x = this.width / 2 - this.columns * this.btnDim / 2;
            this.y = this.height / 2 - this.rows * this.btnDim / 2;
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(this.minecraft.player);
            int cost = Configs.GENERAL.calcCost(trinkets);
            boolean canAfford = this.minecraft.player.isCreative()
                    || cost == 0
                    || this.minecraft.player.experienceLevel >= cost;
            List<ITrinket> all = trinkets.getActiveTrinkets();
            label: for (int j1 = 0; j1 < this.rows; ++j1) {
                for (int j2 = 0; j2 < this.columns; ++j2) {
                    int i = j2 + j1 * this.columns;
                    if (i + 1 <= all.size()) {
                        ITrinket trinket = all.get(i);
                        TrinketButton tb = new TrinketButton(
                                this.x + j2 * this.btnDim, this.y + j1 * this.btnDim,
                                Textures.TRINKET_ACTIVE_BG, trinket,
                                button -> this.minecraft.setScreen(new TrinketOptionScreen(trinket, this)));
                        trinketButtons.add(tb);
                        addRenderableWidget(tb);
                    } else {
                        boolean locked = i + 1 > trinkets.getSlots();
                        if (locked && cost < 0)
                            break label;
                        IconButton slotBtn = new IconButton(
                                this.x + j2 * this.btnDim, this.y + j1 * this.btnDim,
                                locked ? Textures.TRINKET_BG_LOCKED : Textures.TRINKET_BG_ADD,
                                button -> {
                                    if (locked) {
                                        LostTrinkets.NET.toServer(new UnlockSlotPacket());

                                        LostTrinketsAPI.getTrinkets(this.minecraft.player).unlockSlot();
                                        this.minecraft.setScreen(new TrinketsScreen());
                                    } else {
                                        this.minecraft.setScreen(new AvailableTrinketsScreen(this, 0));
                                    }
                                }, this);
                        if (locked) {
                            slotBtn.active = canAfford;
                            slotBtn.setTooltip(list -> {
                                list.add(Component.translatable("gui.losttrinkets.trinket.slot.locked")
                                        .withStyle(ChatFormatting.RED));
                                list.add(Component.translatable("gui.losttrinkets.trinket.slot.cost", cost)
                                        .withStyle(ChatFormatting.YELLOW));
                                list.add(Component.translatable("gui.losttrinkets.trinket.slot.click.unlock")
                                        .withStyle(ChatFormatting.GRAY));
                            });
                        } else {
                            slotBtn.setTooltip(
                                    list -> list.add(Component.translatable("gui.losttrinkets.trinket.slot.click.add")
                                            .withStyle(ChatFormatting.GREEN)));
                        }
                        addRenderableWidget(slotBtn);
                        if (locked)
                            break label;
                    }
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mx, int my, float pt) {
        renderBackground(guiGraphics, mx, my, pt);
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
}
