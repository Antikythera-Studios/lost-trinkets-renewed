package guivnf.losttrinkets.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import guivnf.losttrinkets.LostTrinkets;
import guivnf.losttrinkets.api.trinket.Rarity;
import guivnf.losttrinkets.api.trinket.Trinket;
import guivnf.losttrinkets.item.trinkets.*;
import guivnf.losttrinkets.registry.LTRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Itms {
        public static final LTRegistry<Item> REG = LTRegistry.create(Registries.ITEM, LostTrinkets.MOD_ID);
        public static final List<RegistrySupplier<? extends Item>> ALL_ITEMS = new ArrayList<>();

        public static final RegistrySupplier<Trinket> PIGGY = reg("piggy",
                        props -> new Trinket(Rarity.COMMON, props));
        public static final RegistrySupplier<CreepoTrinket> CREEPO = reg("creepo",
                        props -> new CreepoTrinket(Rarity.COMMON, props));
        public static final RegistrySupplier<HorseshoeTrinket> HORSESHOE = reg("horseshoe",
                        props -> new HorseshoeTrinket(Rarity.COMMON, props));
        public static final RegistrySupplier<ButchersCleaverTrinket> BUTCHERS_CLEAVER = reg("butchers_cleaver",
                        props -> new ButchersCleaverTrinket(Rarity.COMMON, props));
        public static final RegistrySupplier<SlingshotTrinket> SLINGSHOT = reg("slingshot",
                        props -> new SlingshotTrinket(Rarity.COMMON, props));
        public static final RegistrySupplier<MagnetoTrinket> MAGNETO = reg("magneto",
                        props -> new MagnetoTrinket(Rarity.COMMON, props));

        public static final RegistrySupplier<CandyCornTrinket> CANDY_CORN = reg("candy_corn",
                        props -> new CandyCornTrinket(Rarity.UNCOMMON, props)
                                        .add(Attributes.MOVEMENT_SPEED, "71bbf6cf-3a75-4a4c-8dc3-e0960ced8b9c", 0.05D));
        public static final RegistrySupplier<LunchBagTrinket> LUNCH_BAG = reg("lunch_bag",
                        props -> new LunchBagTrinket(Rarity.UNCOMMON, props));
        public static final RegistrySupplier<LuckCoinTrinket> LUCK_COIN = reg("luck_coin",
                        props -> new LuckCoinTrinket(Rarity.UNCOMMON, props));
        public static final RegistrySupplier<MinersPickTrinket> MINERS_PICK = reg("miners_pick",
                        props -> new MinersPickTrinket(Rarity.UNCOMMON, props));
        public static final RegistrySupplier<ThaCloudTrinket> THA_CLOUD = reg("tha_cloud",
                        props -> new ThaCloudTrinket(Rarity.UNCOMMON, props));
        public static final RegistrySupplier<TurtleShellTrinket> TURTLE_SHELL = reg("turtle_shell",
                        props -> new TurtleShellTrinket(Rarity.UNCOMMON, props));
        public static final RegistrySupplier<IceShardTrinket> ICE_SHARD = reg("ice_shard",
                        props -> new IceShardTrinket(Rarity.UNCOMMON, props));

        public static final RegistrySupplier<Trinket> EMPTY_AMULET = reg("empty_amulet",
                        props -> new Trinket(Rarity.RARE, props));
        public static final RegistrySupplier<Trinket> THA_SPIDER = reg("tha_spider",
                        props -> new ThaSpiderTrinket(Rarity.RARE, props));
        public static final RegistrySupplier<Trinket> GLASS_SHARDS = reg("glass_shards",
                        props -> new Trinket(Rarity.RARE, props));
        public static final RegistrySupplier<BlazeHeartTrinket> BLAZE_HEART = reg("blaze_heart",
                        props -> new BlazeHeartTrinket(Rarity.RARE, props));
        public static final RegistrySupplier<ThaGhostTrinket> THA_GHOST = reg("tha_ghost",
                        props -> new ThaGhostTrinket(Rarity.RARE, props));
        public static final RegistrySupplier<TrebleHooksTrinket> TREBLE_HOOKS = reg("treble_hooks",
                        props -> new TrebleHooksTrinket(Rarity.RARE, props));
        public static final RegistrySupplier<ThaWizardTrinket> THA_WIZARD = reg("tha_wizard",
                        props -> new ThaWizardTrinket(Rarity.RARE, props));
        public static final RegistrySupplier<ThaBatTrinket> THA_BAT = reg("tha_bat",
                        props -> new ThaBatTrinket(Rarity.RARE, props));
        public static final RegistrySupplier<Trinket> BLANK_EYES = reg("blank_eyes",
                        props -> new Trinket(Rarity.RARE, props));
        public static final RegistrySupplier<BigFootTrinket> BIG_FOOT = reg("big_foot",
                        props -> new BigFootTrinket(Rarity.RARE, props));

        public static final RegistrySupplier<Trinket> BOOK_O_ENCHANTING = reg("book_o_enchanting",
                        props -> new Trinket(Rarity.MASTER, props));
        public static final RegistrySupplier<WarmVoidTrinket> WARM_VOID = reg("warm_void",
                        props -> new WarmVoidTrinket(Rarity.MASTER, props));
        public static final RegistrySupplier<GoldenMelonTrinket> GOLDEN_MELON = reg("golden_melon",
                        props -> new GoldenMelonTrinket(Rarity.MASTER, props));
        public static final RegistrySupplier<WitherHandTrinket> WITHER_HAND = reg("wither_hand",
                        props -> new WitherHandTrinket(Rarity.MASTER, props));
        public static final RegistrySupplier<SerpentToothTrinket> SERPENT_TOOTH = reg("serpent_tooth",
                        props -> new SerpentToothTrinket(Rarity.MASTER, props));
        public static final RegistrySupplier<MadPiggyTrinket> MAD_PIGGY = reg("mad_piggy",
                        props -> new MadPiggyTrinket(Rarity.MASTER, props));
        public static final RegistrySupplier<Trinket> MINDS_EYE = reg("minds_eye",
                        props -> new Trinket(Rarity.MASTER, props));
        public static final RegistrySupplier<GoldenSwatterTrinket> GOLDEN_SWATTER = reg("golden_swatter",
                        props -> new GoldenSwatterTrinket(Rarity.MASTER, props));
        public static final RegistrySupplier<StickyMindTrinket> STICKY_MIND = reg("sticky_mind",
                        props -> new StickyMindTrinket(Rarity.MASTER, props));
        public static final RegistrySupplier<FireMindTrinket> FIRE_MIND = reg("fire_mind",
                        props -> new FireMindTrinket(Rarity.MASTER, props));
        public static final RegistrySupplier<Trinket> THA_GOLEM = reg("tha_golem",
                        props -> new Trinket(Rarity.MASTER, props)
                                        .add(Attributes.KNOCKBACK_RESISTANCE, "afb13d18-56f2-4e1f-8281-8cc7e3005eef",
                                                        1.0D));
        public static final RegistrySupplier<DragonBreathTrinket> DRAGON_BREATH = reg("dragon_breath",
                        props -> new DragonBreathTrinket(Rarity.MASTER, props));

        public static final RegistrySupplier<Trinket> KARMA = reg("karma",
                        props -> new Trinket(Rarity.ELITE, props));
        public static final RegistrySupplier<DarkDaggerTrinket> DARK_DAGGER = reg("dark_dagger",
                        props -> new DarkDaggerTrinket(Rarity.ELITE, props));
        public static final RegistrySupplier<StarfishTrinket> STARFISH = reg("starfish",
                        props -> new StarfishTrinket(Rarity.ELITE, props));
        public static final RegistrySupplier<DropSpindleTrinket> DROP_SPINDLE = reg("drop_spindle",
                        props -> new DropSpindleTrinket(Rarity.ELITE, props));
        public static final RegistrySupplier<EmberTrinket> EMBER = reg("ember",
                        props -> new EmberTrinket(Rarity.ELITE, props));
        public static final RegistrySupplier<TeaLeafTrinket> TEA_LEAF = reg("tea_leaf",
                        props -> new TeaLeafTrinket(Rarity.ELITE, props));
        public static final RegistrySupplier<CoffeeBeanTrinket> COFFEE_BEAN = reg("coffee_bean",
                        props -> new CoffeeBeanTrinket(Rarity.ELITE, props));
        public static final RegistrySupplier<OxalisTrinket> OXALIS = reg("oxalis",
                        props -> new OxalisTrinket(Rarity.ELITE, props));
        public static final RegistrySupplier<GoldenSkullTrinket> GOLDEN_SKULL = reg("golden_skull",
                        props -> new GoldenSkullTrinket(Rarity.ELITE, props));

        public static final RegistrySupplier<DarkEggTrinket> DARK_EGG = reg("dark_egg",
                        props -> new DarkEggTrinket(Rarity.EPIC, props));
        public static final RegistrySupplier<PillowOfSecretsTrinket> PILLOW_OF_SECRETS = reg("pillow_of_secrets",
                        props -> new PillowOfSecretsTrinket(Rarity.EPIC, props));
        public static final RegistrySupplier<ThaSpiritTrinket> THA_SPIRIT = reg("tha_spirit",
                        props -> new ThaSpiritTrinket(Rarity.EPIC, props));
        public static final RegistrySupplier<MirrorTrinket> MIRROR = reg("mirror",
                        props -> new MirrorTrinket(Rarity.EPIC, props));
        public static final RegistrySupplier<GoldenRingTrinket> GOLDEN_RING = reg("golden_ring",
                        props -> new GoldenRingTrinket(Rarity.EPIC, props));
        public static final RegistrySupplier<GoldenBeltTrinket> GOLDEN_BELT = reg("golden_belt",
                        props -> new GoldenBeltTrinket(Rarity.EPIC, props));
        public static final RegistrySupplier<TreasureRingTrinket> TREASURE_RING = reg("treasure_ring",
                        props -> new TreasureRingTrinket(Rarity.EPIC, props));
        public static final RegistrySupplier<OctopickTrinket> OCTOPICK = reg("octopick",
                        props -> new OctopickTrinket(Rarity.EPIC, props));
        public static final RegistrySupplier<Trinket> SILVER_NAIL = reg("silver_nail",
                        props -> new Trinket(Rarity.EPIC, props));
        public static final RegistrySupplier<Trinket> GLORY_SHARDS = reg("glory_shards",
                        props -> new Trinket(Rarity.EPIC, props));

        public static final RegistrySupplier<Trinket> ASH_GLOVES = reg("ash_gloves",
                        props -> new Trinket(Rarity.LEGENDARY, props)
                                        .add(Attributes.ATTACK_SPEED, "1a71bd06-0d8b-459e-b961-fbd992d61c5d", 1024.0D));
        public static final RegistrySupplier<RubyHeartTrinket> RUBY_HEART = reg("ruby_heart",
                        props -> new RubyHeartTrinket(Rarity.LEGENDARY, props));
        public static final RegistrySupplier<Trinket> GOLDEN_HORSESHOE = reg("golden_horseshoe",
                        props -> new Trinket(Rarity.LEGENDARY, props));
        public static final RegistrySupplier<Trinket> GOLDEN_TOOTH = reg("golden_tooth",
                        props -> new Trinket(Rarity.LEGENDARY, props));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_1 = reg("broken_heart_1",
                        props -> new Trinket(Rarity.LEGENDARY, props)
                                        .add(Attributes.MAX_HEALTH, "092962d0-2711-48e0-9a84-f768ea4aeeb2", 4.0D));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_2 = reg("broken_heart_2",
                        props -> new Trinket(Rarity.LEGENDARY, props)
                                        .add(Attributes.MAX_HEALTH, "bf4f459a-d398-4cc1-a146-9d3828f2201a", 4.0D));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_3 = reg("broken_heart_3",
                        props -> new Trinket(Rarity.LEGENDARY, props)
                                        .add(Attributes.MAX_HEALTH, "cb979db5-2f24-40b5-b2ef-4b7d29491ef4", 4.0D));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_4 = reg("broken_heart_4",
                        props -> new Trinket(Rarity.LEGENDARY, props)
                                        .add(Attributes.MAX_HEALTH, "1816e016-b569-4258-889e-d45829628248", 4.0D));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_5 = reg("broken_heart_5",
                        props -> new Trinket(Rarity.LEGENDARY, props)
                                        .add(Attributes.MAX_HEALTH, "a3fee661-c9e0-40d9-8386-ac245576bed0", 4.0D));
        public static final RegistrySupplier<OctopusLegTrinket> OCTOPUS_LEG = reg("octopus_leg",
                        props -> new OctopusLegTrinket(Rarity.LEGENDARY, props));
        public static final RegistrySupplier<MagicalHerbsTrinket> MAGICAL_HERBS = reg("magical_herbs",
                        props -> new MagicalHerbsTrinket(Rarity.LEGENDARY, props));
        public static final RegistrySupplier<MagicalFeathersTrinket> MAGICAL_FEATHERS = reg("magical_feathers",
                        props -> new MagicalFeathersTrinket(Rarity.LEGENDARY, props));
        public static final RegistrySupplier<MadAuraTrinket> MAD_AURA = reg("mad_aura",
                        props -> new MadAuraTrinket(Rarity.LEGENDARY, props));
        public static final RegistrySupplier<Trinket> BROKEN_TOTEM = reg("broken_totem",
                        props -> new Trinket(Rarity.LEGENDARY, props));

        public static final RegistrySupplier<Item> TREASURE_BAG = regItem("treasure_bag",
                        props -> new TreasureBagItem(props));

        private static <V extends Trinket> RegistrySupplier<V> reg(String name,
                        java.util.function.Function<Item.Properties, V> factory) {
                RegistrySupplier<V> s = REG.register(name, () -> factory.apply(props(name)));
                ALL_ITEMS.add(s);
                return s;
        }

        private static <V extends Item> RegistrySupplier<V> regItem(String name,
                        java.util.function.Function<Item.Properties, V> factory) {
                RegistrySupplier<V> s = REG.register(name, () -> factory.apply(props(name)));
                ALL_ITEMS.add(s);
                return s;
        }

        // MC 26.1 requires an item's registry id to be set on its Properties before the Item is built.
        private static Item.Properties props(String name) {
                return new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM,
                                net.minecraft.resources.Identifier.fromNamespaceAndPath(LostTrinkets.MOD_ID, name)));
        }
}
