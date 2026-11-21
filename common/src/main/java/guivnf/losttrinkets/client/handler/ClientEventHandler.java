package guivnf.losttrinkets.client.handler;

import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.client.util.MC;

public class ClientEventHandler {
    public static void register() {
        // status line right under the trinket name
        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            if (!(stack.getItem() instanceof ITrinket trinket) || lines.isEmpty())
                return;
            Component status = null;
            if (LostTrinketsAPI.get().isDisabled(trinket)) {
                status = Component.translatable("gui.losttrinkets.status.disabled").withStyle(ChatFormatting.DARK_RED);
            } else if (MC.player().map(player -> LostTrinketsAPI.getTrinkets(player).has(trinket)).orElse(false)) {
                status = Component.translatable("gui.losttrinkets.status.owned").withStyle(ChatFormatting.BLUE);
            } else if (LostTrinketsAPI.get().isNonRandom(trinket)) {
                status = Component.translatable("gui.losttrinkets.status.non_random").withStyle(ChatFormatting.DARK_GRAY);
            }
            if (status != null) {
                lines.add(1, status);
            }
        });
    }
}
