package guivnf.losttrinkets.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import dev.architectury.platform.Platform;
import guivnf.losttrinkets.network.packet.*;

public class Packets {
    public static void register() {
        SetActivePacket.register();
        SetInactivePacket.register();
        UnlockSlotPacket.register();
        MagnetoPacket.register();
        // On dedicated server only: register S2C payload types so the server can send them.
        // On client, ClientPacketHandlers.registerAll() handles this via registerReceiver.
        if (Platform.getEnvironment() == Env.SERVER) {
            NetworkManager.registerS2CPayloadType(SyncDataPacket.ID);
            NetworkManager.registerS2CPayloadType(SyncFlyPacket.ID);
            NetworkManager.registerS2CPayloadType(TrinketUnlockedPacket.ID);
        }
    }
}
