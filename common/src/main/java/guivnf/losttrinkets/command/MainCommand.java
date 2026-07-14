package guivnf.losttrinkets.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.trinket.ITrinket;
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
                                }))
                                .then(Commands.literal("trinket")
                                        .then(Commands.argument("trinket", ResourceLocationArgument.id())
                                                .suggests((ctx, builder) -> SharedSuggestionProvider.suggestResource(
                                                        UnlockManager.getTrinkets().stream()
                                                                .map(t -> BuiltInRegistries.ITEM.getKey(t.getItem())),
                                                        builder))
                                                .executes(context -> {
                                                    Collection<ServerPlayer> players = EntityArgument.getPlayers(context,
                                                            "targets");
                                                    ResourceLocation id = ResourceLocationArgument.getId(context,
                                                            "trinket");
                                                    Item item = BuiltInRegistries.ITEM.get(id);
                                                    if (!(item instanceof ITrinket trinket)) {
                                                        context.getSource().sendFailure(Component.translatable(
                                                                "chat.losttrinkets.unlock.invalid", id.toString()));
                                                        return 0;
                                                    }
                                                    players.forEach(player -> UnlockManager.unlock(player, trinket, false,
                                                            false));
                                                    Component name = new ItemStack(item).getHoverName();
                                                    context.getSource().sendSuccess(() -> Component.translatable(
                                                            "chat.losttrinkets.unlocked.specific", name), true);
                                                    return players.size();
                                                })))))
                .then(Commands.literal("clear")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.players()).executes(context -> {
                            Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "targets");
                            players.forEach(player -> LostTrinketsAPI.getTrinkets(player).clear());
                            return players.size();
                        }))));
    }
}
