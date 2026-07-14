package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class DragonBreathTrinket extends Trinket<DragonBreathTrinket> {
    public DragonBreathTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static List<ItemStack> autoSmelt(List<ItemStack> stacks, Player player) {
        Holder<Enchantment> silkTouch = player.level().registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
        if (EnchantmentHelper.getItemEnchantmentLevel(silkTouch, player.getMainHandItem()) <= 0) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (trinkets.isActive(Itms.DRAGON_BREATH.get())) {
                List<ItemStack> smelted = new ArrayList<>();
                List<ItemStack> remaining = new ArrayList<>(stacks);
                Iterator<ItemStack> itr = remaining.iterator();
                while (itr.hasNext()) {
                    ItemStack in = itr.next();
                    SingleRecipeInput recipeInput = new SingleRecipeInput(in);
                    Optional<RecipeHolder<SmeltingRecipe>> recipe = player.level().getServer().getRecipeManager()
                            .getRecipeFor(RecipeType.SMELTING, recipeInput, player.level());
                    if (recipe.isPresent()) {
                        ItemStack output = recipe.get().value().assemble(recipeInput).copy();
                        if (!output.isEmpty()) {
                            output.setCount(output.getCount() * in.getCount());
                            smelted.add(output);
                            itr.remove();
                        }
                    }
                }
                smelted.addAll(remaining);
                return smelted;
            }
        }
        return stacks;
    }
}
