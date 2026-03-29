package guivnf.losttrinkets.item.trinkets;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.client.handler.KeyHandler;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.network.packet.MagnetoPacket;
import guivnf.losttrinkets.util.Magnet;

import java.util.List;

public class MagnetoTrinket extends Trinket<MagnetoTrinket> {
    public MagnetoTrinket(Rarity rarity, Properties properties) {
        super(rarity, properties);
    }

    public static void trySendCollect(Player player) {
        LostTrinkets.NET.toServer(new MagnetoPacket());
    }

    public static void tryCollectServer(Player player) {
        if (!LostTrinketsAPI.getTrinkets(player).isActive(Itms.MAGNETO.get()))
            return;
        AABB bb = new AABB(player.blockPosition()).inflate(10);
        player.level().getEntitiesOfClass(ItemEntity.class, bb)
                .stream().filter(Magnet::canCollectManual)
                .forEach(entity -> {
                    entity.setNoPickUpDelay();
                    entity.playerTouch(player);
                });
        player.level().getEntitiesOfClass(ExperienceOrb.class, bb)
                .stream().filter(Magnet::canCollectManualOrb)
                .forEach(orb -> orb.playerTouch(player));
    }

    @Override
    public void addTrinketDescription(ItemStack stack, List<Component> lines) {
        super.addTrinketDescription(stack, lines);
        lines.add(KeyHandler.getMagnetoHint());
    }
}
