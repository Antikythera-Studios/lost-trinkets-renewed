package guivnf.losttrinkets.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.handler.UnlockManager;

import java.util.Collection;

public class MainCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(LostTrinkets.MOD_ID)
                .then(Commands.literal("unlock")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.literal("all").executes(context -> {
                                    Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "targets");
                                    players.forEach(player -> UnlockManager.getTrinkets()
                                            .forEach(trinket -> UnlockManager.unlock(player, trinket, false, false)));
                                    context.getSource().sendSuccess(
                                            () -> Component.translatable("chat.losttrinkets.unlocked.all"), true);
                                    return players.size();
                                }))
                                .then(Commands.literal("random").executes(context -> {
                                    Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "targets");
                                    players.forEach(LostTrinketsAPI.get()::unlock);
                                    return players.size();
                                }))))
                .then(Commands.literal("clear")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.players()).executes(context -> {
                            Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "targets");
                            players.forEach(player -> LostTrinketsAPI.getTrinkets(player).clear());
                            return players.size();
                        }))));
    }
}
