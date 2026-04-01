package guivnf.losttrinkets.item.trinkets;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.api.trinket.ITickableTrinket;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.network.packet.SyncFlyPacket;

public class MagicalFeathersTrinket extends Trinket<MagicalFeathersTrinket> implements ITickableTrinket {
    public MagicalFeathersTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    @Override
    public void tick(Level level, BlockPos pos, Player player) {
        PlayerData data = LostTrinketsAPI.getData(player);
        player.getAbilities().mayfly = true;
        if (data.wasFlying) {
            player.getAbilities().flying = true;
            data.wasFlying = false;
        }
        if (!data.allowFlying) {
            data.allowFlying = true;
            if (!level.isClientSide && player instanceof ServerPlayer sp) {

                sp.onUpdateAbilities();
                LostTrinkets.NET.toClient(new SyncFlyPacket(true), player);
            }
        }
    }

    @Override
    public void onDeactivated(Level level, BlockPos pos, Player player) {
        super.onDeactivated(level, pos, player);
        PlayerData data = LostTrinketsAPI.getData(player);
        if (data.allowFlying) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            data.allowFlying = false;
            if (!level.isClientSide && player instanceof ServerPlayer sp) {
                sp.onUpdateAbilities();
                LostTrinkets.NET.toClient(new SyncFlyPacket(false), player);
            }
        }
    }
}
