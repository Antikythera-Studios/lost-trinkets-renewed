package guivnf.losttrinkets.network;

import guivnf.losttrinkets.network.packet.*;

public class Packets {
    public static void register() {
        SetActivePacket.register();
        SetInactivePacket.register();
        UnlockSlotPacket.register();
        MagnetoPacket.register();
    }
}
