package guivnf.losttrinkets.api.trinket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.List;

public interface ITrinket {
    default void addTrinketDescription(ItemStack stack, List<Component> lines) {
        // info.losttrinkets.<name>
        String key = stack.getItem().getDescriptionId().replace("item.", "info.");
        lines.add(Component.translatable(key).withStyle(net.minecraft.ChatFormatting.GRAY));
    }

    void onActivated(Level level, BlockPos pos, Player player);

    void onDeactivated(Level level, BlockPos pos, Player player);

    Rarity getRarity();

    boolean isUnlockable();

    void setUnlockable(boolean unlockable);

    Item getItem();
}
