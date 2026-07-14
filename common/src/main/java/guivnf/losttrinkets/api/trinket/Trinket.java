package guivnf.losttrinkets.api.trinket;

import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.client.util.MC;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Trinket<T extends Trinket<T>> extends Item implements ITrinket {

    private final Map<Holder<Attribute>, AttributeModifier> attributes = Maps.newHashMap();
    private final Rarity rarity;
    protected boolean unlockable = true;

    public Trinket(Rarity rarity, Properties properties) {
        super(properties.stacksTo(1));
        this.rarity = rarity;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (LostTrinketsAPI.get().unlock(player, this)) {
            ItemStack stack = player.getItemInHand(hand);
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return InteractionResult.CONSUME;
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context,
            net.minecraft.world.item.component.TooltipDisplay display,
            java.util.function.Consumer<Component> tooltip, TooltipFlag flag) {
        java.util.List<Component> lines = new java.util.ArrayList<>();
        if (LostTrinketsAPI.get().isDisabled(this)) {
            lines.add(Component.translatable("gui.losttrinkets.status.disabled").withStyle(ChatFormatting.DARK_RED));
        } else {
            Player player = MC.player().orElse(null);
            if (player != null && LostTrinketsAPI.getTrinkets(player).has(this)) {
                lines.add(Component.translatable("gui.losttrinkets.status.owned").withStyle(ChatFormatting.BLUE));
            } else if (LostTrinketsAPI.get().isNonRandom(this)) {
                lines.add(Component.translatable("gui.losttrinkets.status.non_random").withStyle(ChatFormatting.DARK_GRAY));
            }
        }
        addTrinketDescription(stack, lines);
        lines.add(Component.translatable("gui.losttrinkets.rarity." + getRarity().name().toLowerCase(Locale.ENGLISH)).withStyle(ChatFormatting.DARK_GRAY));
        lines.forEach(tooltip);
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(this.getRarity().getStyle());
    }

    @Override
    public void onActivated(Level level, BlockPos pos, Player player) {}

    @Override
    public void onDeactivated(Level level, BlockPos pos, Player player) {}

    @Override
    public Rarity getRarity() {
        return this.rarity;
    }

    @Override
    public boolean isUnlockable() {
        return this.unlockable;
    }

    public Trinket<T> noUnlock() {
        this.unlockable = false;
        return this;
    }

    @Override
    public void setUnlockable(boolean unlockable) {
        this.unlockable = unlockable;
    }

    @SuppressWarnings("unchecked")
    public T add(Holder<Attribute> attribute, String uuid, double amount) {
        AttributeModifier modifier = new AttributeModifier(
                Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, uuid),
                amount, AttributeModifier.Operation.ADD_VALUE);
        getAttributes().put(attribute, modifier);
        return (T) this;
    }

    public void applyAttributes(Player player) {
        for (Map.Entry<Holder<Attribute>, AttributeModifier> entry : getAttributes().entrySet()) {
            AttributeInstance attribute = player.getAttribute(entry.getKey());
            if (attribute != null) {
                AttributeModifier modifier = entry.getValue();
                if (!attribute.hasModifier(modifier.id())) {
                    attribute.addPermanentModifier(modifier);
                }
            }
        }
    }

    public void removeAttributes(Player player) {
        for (Map.Entry<Holder<Attribute>, AttributeModifier> entry : getAttributes().entrySet()) {
            AttributeInstance attribute = player.getAttribute(entry.getKey());
            if (attribute != null) {
                attribute.removeModifier(entry.getValue());
            }
        }
    }

    public Map<Holder<Attribute>, AttributeModifier> getAttributes() {
        return this.attributes;
    }

    @Override
    public Item getItem() {
        return this;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
