package guivnf.losttrinkets.core.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import guivnf.losttrinkets.api.player.IPlayerDataHolder;
import guivnf.losttrinkets.api.player.PlayerData;

@Mixin(Player.class)
public abstract class PlayerDataMixin implements IPlayerDataHolder {
    @Unique
    private final PlayerData losttrinkets$playerData = new PlayerData();

    @Override
    public PlayerData losttrinkets$getPlayerData() {
        return this.losttrinkets$playerData;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void ltSave(CompoundTag nbt, CallbackInfo ci) {
        nbt.put("LostTrinkets", this.losttrinkets$playerData.serializeNBT());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void ltLoad(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("LostTrinkets")) {
            this.losttrinkets$playerData.deserializeNBT(nbt.getCompound("LostTrinkets"));
        }
    }
}
