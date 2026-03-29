package guivnf.losttrinkets.network;

import guivnf.losttrinkets.network.packet.*;

public class Packets {
    public static void register() {
        SyncDataPacket.register();
        SetActivePacket.register();
        SetInactivePacket.register();
        UnlockSlotPacket.register();
        TrinketUnlockedPacket.register();
        SyncFlyPacket.register();
        MagnetoPacket.register();
    }
}
