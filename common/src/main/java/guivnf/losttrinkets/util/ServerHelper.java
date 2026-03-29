package guivnf.losttrinkets.util;

import net.minecraft.server.MinecraftServer;

import org.jetbrains.annotations.Nullable;

public class ServerHelper {
    @Nullable
    private static MinecraftServer server;

    public static void setServer(@Nullable MinecraftServer srv) {
        server = srv;
    }

    @Nullable
    public static MinecraftServer get() {
        return server;
    }
}
