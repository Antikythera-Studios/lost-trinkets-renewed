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
                        () -> new Trinket(Rarity.COMMON, new Item.Properties()));
        public static final RegistrySupplier<CreepoTrinket> CREEPO = reg("creepo",
                        () -> new CreepoTrinket(Rarity.COMMON, new Item.Properties()));
        public static final RegistrySupplier<HorseshoeTrinket> HORSESHOE = reg("horseshoe",
                        () -> new HorseshoeTrinket(Rarity.COMMON, new Item.Properties()));
        public static final RegistrySupplier<ButchersCleaverTrinket> BUTCHERS_CLEAVER = reg("butchers_cleaver",
                        () -> new ButchersCleaverTrinket(Rarity.COMMON, new Item.Properties()));
        public static final RegistrySupplier<SlingshotTrinket> SLINGSHOT = reg("slingshot",
                        () -> new SlingshotTrinket(Rarity.COMMON, new Item.Properties()));
        public static final RegistrySupplier<MagnetoTrinket> MAGNETO = reg("magneto",
                        () -> new MagnetoTrinket(Rarity.COMMON, new Item.Properties()));

        public static final RegistrySupplier<CandyCornTrinket> CANDY_CORN = reg("candy_corn",
                        () -> new CandyCornTrinket(Rarity.UNCOMMON, new Item.Properties())
                                        .add(Attributes.MOVEMENT_SPEED, "71bbf6cf-3a75-4a4c-8dc3-e0960ced8b9c", 0.05D));
        public static final RegistrySupplier<LunchBagTrinket> LUNCH_BAG = reg("lunch_bag",
                        () -> new LunchBagTrinket(Rarity.UNCOMMON, new Item.Properties()));
        public static final RegistrySupplier<LuckCoinTrinket> LUCK_COIN = reg("luck_coin",
                        () -> new LuckCoinTrinket(Rarity.UNCOMMON, new Item.Properties()));
        public static final RegistrySupplier<MinersPickTrinket> MINERS_PICK = reg("miners_pick",
                        () -> new MinersPickTrinket(Rarity.UNCOMMON, new Item.Properties()));
        public static final RegistrySupplier<ThaCloudTrinket> THA_CLOUD = reg("tha_cloud",
                        () -> new ThaCloudTrinket(Rarity.UNCOMMON, new Item.Properties()));
        public static final RegistrySupplier<TurtleShellTrinket> TURTLE_SHELL = reg("turtle_shell",
                        () -> new TurtleShellTrinket(Rarity.UNCOMMON, new Item.Properties()));
        public static final RegistrySupplier<IceShardTrinket> ICE_SHARD = reg("ice_shard",
                        () -> new IceShardTrinket(Rarity.UNCOMMON, new Item.Properties()));

        public static final RegistrySupplier<Trinket> EMPTY_AMULET = reg("empty_amulet",
                        () -> new Trinket(Rarity.RARE, new Item.Properties()));
        public static final RegistrySupplier<Trinket> THA_SPIDER = reg("tha_spider",
                        () -> new ThaSpiderTrinket(Rarity.RARE, new Item.Properties()));
        public static final RegistrySupplier<Trinket> GLASS_SHARDS = reg("glass_shards",
                        () -> new Trinket(Rarity.RARE, new Item.Properties()));
        public static final RegistrySupplier<BlazeHeartTrinket> BLAZE_HEART = reg("blaze_heart",
                        () -> new BlazeHeartTrinket(Rarity.RARE, new Item.Properties()));
        public static final RegistrySupplier<ThaGhostTrinket> THA_GHOST = reg("tha_ghost",
                        () -> new ThaGhostTrinket(Rarity.RARE, new Item.Properties()));
        public static final RegistrySupplier<TrebleHooksTrinket> TREBLE_HOOKS = reg("treble_hooks",
                        () -> new TrebleHooksTrinket(Rarity.RARE, new Item.Properties()));
        public static final RegistrySupplier<ThaWizardTrinket> THA_WIZARD = reg("tha_wizard",
                        () -> new ThaWizardTrinket(Rarity.RARE, new Item.Properties()));
        public static final RegistrySupplier<ThaBatTrinket> THA_BAT = reg("tha_bat",
                        () -> new ThaBatTrinket(Rarity.RARE, new Item.Properties()));
        public static final RegistrySupplier<Trinket> BLANK_EYES = reg("blank_eyes",
                        () -> new Trinket(Rarity.RARE, new Item.Properties()));
        public static final RegistrySupplier<BigFootTrinket> BIG_FOOT = reg("big_foot",
                        () -> new BigFootTrinket(Rarity.RARE, new Item.Properties()));

        public static final RegistrySupplier<Trinket> BOOK_O_ENCHANTING = reg("book_o_enchanting",
                        () -> new Trinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<WarmVoidTrinket> WARM_VOID = reg("warm_void",
                        () -> new WarmVoidTrinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<GoldenMelonTrinket> GOLDEN_MELON = reg("golden_melon",
                        () -> new GoldenMelonTrinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<WitherHandTrinket> WITHER_HAND = reg("wither_hand",
                        () -> new WitherHandTrinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<SerpentToothTrinket> SERPENT_TOOTH = reg("serpent_tooth",
                        () -> new SerpentToothTrinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<MadPiggyTrinket> MAD_PIGGY = reg("mad_piggy",
                        () -> new MadPiggyTrinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<Trinket> MINDS_EYE = reg("minds_eye",
                        () -> new Trinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<GoldenSwatterTrinket> GOLDEN_SWATTER = reg("golden_swatter",
                        () -> new GoldenSwatterTrinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<StickyMindTrinket> STICKY_MIND = reg("sticky_mind",
                        () -> new StickyMindTrinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<FireMindTrinket> FIRE_MIND = reg("fire_mind",
                        () -> new FireMindTrinket(Rarity.MASTER, new Item.Properties()));
        public static final RegistrySupplier<Trinket> THA_GOLEM = reg("tha_golem",
                        () -> new Trinket(Rarity.MASTER, new Item.Properties())
                                        .add(Attributes.KNOCKBACK_RESISTANCE, "afb13d18-56f2-4e1f-8281-8cc7e3005eef",
                                                        1.0D));
        public static final RegistrySupplier<DragonBreathTrinket> DRAGON_BREATH = reg("dragon_breath",
                        () -> new DragonBreathTrinket(Rarity.MASTER, new Item.Properties()));

        public static final RegistrySupplier<Trinket> KARMA = reg("karma",
                        () -> new Trinket(Rarity.ELITE, new Item.Properties()));
        public static final RegistrySupplier<DarkDaggerTrinket> DARK_DAGGER = reg("dark_dagger",
                        () -> new DarkDaggerTrinket(Rarity.ELITE, new Item.Properties()));
        public static final RegistrySupplier<StarfishTrinket> STARFISH = reg("starfish",
                        () -> new StarfishTrinket(Rarity.ELITE, new Item.Properties()));
        public static final RegistrySupplier<DropSpindleTrinket> DROP_SPINDLE = reg("drop_spindle",
                        () -> new DropSpindleTrinket(Rarity.ELITE, new Item.Properties()));
        public static final RegistrySupplier<EmberTrinket> EMBER = reg("ember",
                        () -> new EmberTrinket(Rarity.ELITE, new Item.Properties()));
        public static final RegistrySupplier<TeaLeafTrinket> TEA_LEAF = reg("tea_leaf",
                        () -> new TeaLeafTrinket(Rarity.ELITE, new Item.Properties()));
        public static final RegistrySupplier<CoffeeBeanTrinket> COFFEE_BEAN = reg("coffee_bean",
                        () -> new CoffeeBeanTrinket(Rarity.ELITE, new Item.Properties()));
        public static final RegistrySupplier<OxalisTrinket> OXALIS = reg("oxalis",
                        () -> new OxalisTrinket(Rarity.ELITE, new Item.Properties()));
        public static final RegistrySupplier<GoldenSkullTrinket> GOLDEN_SKULL = reg("golden_skull",
                        () -> new GoldenSkullTrinket(Rarity.ELITE, new Item.Properties()));

        public static final RegistrySupplier<DarkEggTrinket> DARK_EGG = reg("dark_egg",
                        () -> new DarkEggTrinket(Rarity.EPIC, new Item.Properties()));
        public static final RegistrySupplier<PillowOfSecretsTrinket> PILLOW_OF_SECRETS = reg("pillow_of_secrets",
                        () -> new PillowOfSecretsTrinket(Rarity.EPIC, new Item.Properties()));
        public static final RegistrySupplier<ThaSpiritTrinket> THA_SPIRIT = reg("tha_spirit",
                        () -> new ThaSpiritTrinket(Rarity.EPIC, new Item.Properties()));
        public static final RegistrySupplier<MirrorTrinket> MIRROR = reg("mirror",
                        () -> new MirrorTrinket(Rarity.EPIC, new Item.Properties()));
        public static final RegistrySupplier<GoldenRingTrinket> GOLDEN_RING = reg("golden_ring",
                        () -> new GoldenRingTrinket(Rarity.EPIC, new Item.Properties()));
        public static final RegistrySupplier<GoldenBeltTrinket> GOLDEN_BELT = reg("golden_belt",
                        () -> new GoldenBeltTrinket(Rarity.EPIC, new Item.Properties()));
        public static final RegistrySupplier<TreasureRingTrinket> TREASURE_RING = reg("treasure_ring",
                        () -> new TreasureRingTrinket(Rarity.EPIC, new Item.Properties()));
        public static final RegistrySupplier<OctopickTrinket> OCTOPICK = reg("octopick",
                        () -> new OctopickTrinket(Rarity.EPIC, new Item.Properties()));
        public static final RegistrySupplier<Trinket> SILVER_NAIL = reg("silver_nail",
                        () -> new Trinket(Rarity.EPIC, new Item.Properties()));
        public static final RegistrySupplier<Trinket> GLORY_SHARDS = reg("glory_shards",
                        () -> new Trinket(Rarity.EPIC, new Item.Properties()));

        public static final RegistrySupplier<Trinket> ASH_GLOVES = reg("ash_gloves",
                        () -> new Trinket(Rarity.LEGENDARY, new Item.Properties())
                                        .add(Attributes.ATTACK_SPEED, "1a71bd06-0d8b-459e-b961-fbd992d61c5d", 1024.0D));
        public static final RegistrySupplier<RubyHeartTrinket> RUBY_HEART = reg("ruby_heart",
                        () -> new RubyHeartTrinket(Rarity.LEGENDARY, new Item.Properties()));
        public static final RegistrySupplier<Trinket> GOLDEN_HORSESHOE = reg("golden_horseshoe",
                        () -> new Trinket(Rarity.LEGENDARY, new Item.Properties()));
        public static final RegistrySupplier<Trinket> GOLDEN_TOOTH = reg("golden_tooth",
                        () -> new Trinket(Rarity.LEGENDARY, new Item.Properties()));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_1 = reg("broken_heart_1",
                        () -> new Trinket(Rarity.LEGENDARY, new Item.Properties())
                                        .add(Attributes.MAX_HEALTH, "092962d0-2711-48e0-9a84-f768ea4aeeb2", 4.0D));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_2 = reg("broken_heart_2",
                        () -> new Trinket(Rarity.LEGENDARY, new Item.Properties())
                                        .add(Attributes.MAX_HEALTH, "bf4f459a-d398-4cc1-a146-9d3828f2201a", 4.0D));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_3 = reg("broken_heart_3",
                        () -> new Trinket(Rarity.LEGENDARY, new Item.Properties())
                                        .add(Attributes.MAX_HEALTH, "cb979db5-2f24-40b5-b2ef-4b7d29491ef4", 4.0D));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_4 = reg("broken_heart_4",
                        () -> new Trinket(Rarity.LEGENDARY, new Item.Properties())
                                        .add(Attributes.MAX_HEALTH, "1816e016-b569-4258-889e-d45829628248", 4.0D));
        public static final RegistrySupplier<Trinket> BROKEN_HEART_5 = reg("broken_heart_5",
                        () -> new Trinket(Rarity.LEGENDARY, new Item.Properties())
                                        .add(Attributes.MAX_HEALTH, "a3fee661-c9e0-40d9-8386-ac245576bed0", 4.0D));
        public static final RegistrySupplier<OctopusLegTrinket> OCTOPUS_LEG = reg("octopus_leg",
                        () -> new OctopusLegTrinket(Rarity.LEGENDARY, new Item.Properties()));
        public static final RegistrySupplier<MagicalHerbsTrinket> MAGICAL_HERBS = reg("magical_herbs",
                        () -> new MagicalHerbsTrinket(Rarity.LEGENDARY, new Item.Properties()));
        public static final RegistrySupplier<MagicalFeathersTrinket> MAGICAL_FEATHERS = reg("magical_feathers",
                        () -> new MagicalFeathersTrinket(Rarity.LEGENDARY, new Item.Properties()));
        public static final RegistrySupplier<MadAuraTrinket> MAD_AURA = reg("mad_aura",
                        () -> new MadAuraTrinket(Rarity.LEGENDARY, new Item.Properties()));
        public static final RegistrySupplier<Trinket> BROKEN_TOTEM = reg("broken_totem",
                        () -> new Trinket(Rarity.LEGENDARY, new Item.Properties()));

        public static final RegistrySupplier<Item> TREASURE_BAG = regItem("treasure_bag",
                        () -> new TreasureBagItem(new Item.Properties()));

        private static <V extends Trinket> RegistrySupplier<V> reg(String name, Supplier<V> supplier) {
                RegistrySupplier<V> s = REG.register(name, supplier);
                ALL_ITEMS.add(s);
                return s;
        }

        private static <V extends Item> RegistrySupplier<V> regItem(String name, Supplier<V> supplier) {
                RegistrySupplier<V> s = REG.register(name, supplier);
                ALL_ITEMS.add(s);
                return s;
        }
}
