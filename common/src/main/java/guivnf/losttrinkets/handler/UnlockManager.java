package guivnf.losttrinkets.handler;

import com.google.common.collect.Sets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.LostTrinketsAPI;
import guivnf.losttrinkets.api.player.PlayerData;
import guivnf.losttrinkets.api.trinket.ITrinket;
import guivnf.losttrinkets.api.trinket.Trinkets;
import guivnf.losttrinkets.config.Configs;
import guivnf.losttrinkets.network.packet.TrinketUnlockedPacket;
import guivnf.losttrinkets.util.ServerHelper;

import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class UnlockManager {

    private static final Set<ITrinket> ALL_TRINKETS = new LinkedHashSet<>();
    private static final Set<ITrinket> TRINKETS = Sets.newLinkedHashSet();
    private static final Set<ITrinket> RANDOM_TRINKETS = Sets.newLinkedHashSet();
    private static final List<WeightedTrinket> WEIGHTED_TRINKETS = new ArrayList<>();

    public static void init() {
        ALL_TRINKETS.clear();
        BuiltInRegistries.ITEM.stream()
                .filter(item -> item instanceof ITrinket)
                .map(item -> (ITrinket) item)
                .forEach(ALL_TRINKETS::add);
        TRINKETS.addAll(ALL_TRINKETS);
        RANDOM_TRINKETS.addAll(ALL_TRINKETS);
    }

    @Nullable
    public static ITrinket unlock(Player player, boolean checkDelay) {
        PlayerData data = LostTrinketsAPI.getData(player);
        if (!checkDelay || data.unlockDelay <= 0) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            WEIGHTED_TRINKETS.clear();
            WEIGHTED_TRINKETS.addAll(RANDOM_TRINKETS.stream()
                    .filter(trinket -> !trinkets.has(trinket))
                    .map(WeightedTrinket::new)
                    .collect(Collectors.toList()));
            if (!WEIGHTED_TRINKETS.isEmpty()) {
                ITrinket selected = weightedRandom(player.level().getRandom());
                if (selected != null) {
                    unlock(player, selected, checkDelay);
                }
            }
        }
        return null;
    }

    @Nullable
    private static ITrinket weightedRandom(net.minecraft.util.RandomSource random) {
        int totalWeight = WEIGHTED_TRINKETS.stream().mapToInt(t -> t.trinket.getRarity().getWeight()).sum();
        if (totalWeight == 0)
            return null;
        int pick = random.nextInt(totalWeight);
        int current = 0;
        for (WeightedTrinket wt : WEIGHTED_TRINKETS) {
            current += wt.trinket.getRarity().getWeight();
            if (pick < current)
                return wt.trinket;
        }
        return null;
    }

    public static boolean unlock(Player player, ITrinket trinket, boolean checkDelay) {
        return unlock(player, trinket, checkDelay, true);
    }

    public static boolean unlock(Player player, ITrinket trinket, boolean checkDelay, boolean doNotification) {
        PlayerData data = LostTrinketsAPI.getData(player);
        if (!checkDelay || data.unlockDelay <= 0) {
            Trinkets trinkets = LostTrinketsAPI.getTrinkets(player);
            if (LostTrinketsAPI.get().isEnabled(trinket) && trinkets.give(trinket)) {
                if (checkDelay) {
                    data.unlockDelay = Configs.GENERAL.unlockCooldown;
                }
                if (doNotification) {
                    ResourceLocation rl = BuiltInRegistries.ITEM.getKey(trinket.getItem());
                    LostTrinkets.NET.toClient(new TrinketUnlockedPacket(Objects.requireNonNull(rl).toString()), player);
                    ItemStack stack = new ItemStack(trinket.getItem());
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                            new HoverEvent.ItemStackInfo(stack));
                    Component trinketName = stack.getHoverName().copy().withStyle(s -> s.withHoverEvent(hoverEvent));
                    Component msg = Component.translatable("chat.losttrinkets.unlocked.trinket",
                            player.getDisplayName(), trinketName);
                    MinecraftServer server = ServerHelper.get();
                    if (server != null) {
                        server.getPlayerList().getPlayers().forEach(p -> p.sendSystemMessage(msg));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static void refresh() {
        init();

        Set<ResourceLocation> banned = Configs.GENERAL.blackList.stream()
                .map(ResourceLocation::new)
                .collect(Collectors.toCollection(Sets::newLinkedHashSet));
        Set<ResourceLocation> nonRandom = Configs.GENERAL.nonRandom.stream()
                .map(ResourceLocation::new)
                .collect(Collectors.toCollection(Sets::newLinkedHashSet));
        Set<ResourceLocation> seen = Sets.newLinkedHashSet();

        LostTrinkets.LOGGER.info("Gathering Trinkets...");
        ALL_TRINKETS.forEach(trinket -> {
            ResourceLocation rl = BuiltInRegistries.ITEM.getKey(trinket.getItem());
            seen.add(rl);
            if (banned.contains(rl)) {
                TRINKETS.remove(trinket);
                RANDOM_TRINKETS.remove(trinket);
                LostTrinkets.LOGGER.info("Banned: " + rl);
            } else {
                TRINKETS.add(trinket);
                if (trinket.isUnlockable() && !nonRandom.contains(rl)) {
                    RANDOM_TRINKETS.add(trinket);
                    LostTrinkets.LOGGER.debug("Enabled: " + rl);
                } else {
                    RANDOM_TRINKETS.remove(trinket);
                    LostTrinkets.LOGGER.info("Non-Random: " + rl);
                }
            }
        });

        LostTrinkets.LOGGER.info("All: " + ALL_TRINKETS.size());
        LostTrinkets.LOGGER
                .info("Enabled: " + TRINKETS.size() + " Disabled: " + (ALL_TRINKETS.size() - TRINKETS.size()));
        LostTrinkets.LOGGER.info(
                "Random: " + RANDOM_TRINKETS.size() + " Non-Random: " + (TRINKETS.size() - RANDOM_TRINKETS.size()));

        banned.stream().filter(rl -> !seen.contains(rl))
                .forEach(rl -> LostTrinkets.LOGGER.warn("Unknown Banned Trinket: " + rl));
        nonRandom.stream().filter(rl -> !seen.contains(rl))
                .forEach(rl -> LostTrinkets.LOGGER.warn("Unknown Non-Random Trinket: " + rl));
        nonRandom.stream().filter(banned::contains)
                .forEach(rl -> LostTrinkets.LOGGER.warn("Redundant Non-Random Trinket (already banned): " + rl));

        MinecraftServer server = ServerHelper.get();
        if (server != null) {
            server.execute(() -> ServerHelper.get().getPlayerList().getPlayers()
                    .forEach(player -> LostTrinketsAPI.getTrinkets(player).removeDisabled(player)));
        }
    }

    public static Set<ITrinket> getTrinkets() {
        return Collections.unmodifiableSet(TRINKETS);
    }

    public static Set<ITrinket> getRandomTrinkets() {
        return Collections.unmodifiableSet(RANDOM_TRINKETS);
    }

    static class WeightedTrinket {

        final ITrinket trinket;

        WeightedTrinket(ITrinket trinket) {
            this.trinket = trinket;
        }
    }
}
