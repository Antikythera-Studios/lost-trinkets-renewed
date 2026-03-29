package guivnf.losttrinkets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.block.Blcks;
import guivnf.losttrinkets.client.Sounds;
import guivnf.losttrinkets.entity.Entities;
import guivnf.losttrinkets.impl.LostTrinketsAPIImpl;
import guivnf.losttrinkets.item.ItemGroups;
import guivnf.losttrinkets.item.Itms;
import guivnf.losttrinkets.network.LTNetwork;
import guivnf.losttrinkets.network.Packets;

public class LostTrinkets {

    public static final String MOD_ID = "losttrinkets";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final LTNetwork NET = new LTNetwork(MOD_ID);

    public static void init() {
        Blcks.REG.init();
        Itms.REG.init();
        Entities.REG.init();
        Sounds.REG.init();
        ItemGroups.REG.init();
        LostTrinketsAPI.init(new LostTrinketsAPIImpl());
    }

    public static void setup() {
        Packets.register();
    }
}
