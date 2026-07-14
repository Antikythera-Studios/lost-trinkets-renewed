package guivnf.losttrinkets.client.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.handler.UnlockManager;

@JeiPlugin
public class JEI implements IModPlugin {
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        UnlockManager.getTrinkets().stream().map(t -> new ItemStack(t.getItem())).forEach(stack -> {
            Identifier key = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem());
            registration.addIngredientInfo(stack, VanillaTypes.ITEM_STACK,
                    net.minecraft.network.chat.Component
                            .translatable("info." + key.getNamespace() + "." + key.getPath().replace('/', '.')));
        });
    }

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, "main");
    }
}
