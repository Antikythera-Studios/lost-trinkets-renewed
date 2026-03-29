package guivnf.losttrinkets.forge.mixin;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.spongepowered.asm.mixin.Mixin;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.item.Itms;

@Mixin(ItemStack.class)
public abstract class ForgeItemStackMixin implements IForgeItemStack {

    @Override
    public boolean isEnderMask(Player player, EnderMan endermanEntity) {
        ItemStack self = (ItemStack)(Object) this;
        boolean enderMask = self.getItem().isEnderMask(self, player, endermanEntity);
        if (!enderMask) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            return trinkets.isActive(Itms.BLANK_EYES.get());
        }
        return true;
    }
}
