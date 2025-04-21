package net.alexioschiu.psycho_artifacts.item;

import net.alexioschiu.psycho_artifacts.PsychoArtifacts;
import net.alexioschiu.psycho_artifacts.item.artifacts.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArtifactsItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PsychoArtifacts.MOD_ID);

    public static final RegistryObject<Item> STAR_OF_MEOW = ITEMS.register("star_of_meow",
            () -> new StarOfMeowItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_CLOUDCRANE = ITEMS.register("star_of_cloudcrane",
            () -> new StarOfCloudcraneItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_YINHE = ITEMS.register("star_of_yinhe",
            () -> new StarOfYinheItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_BAKA = ITEMS.register("star_of_baka",
            () -> new StarOfBakaItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_TONY = ITEMS.register("star_of_tony",
            () -> new StarOfTonyItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_OB = ITEMS.register("star_of_ob",
            () -> new StarOfObItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_RED = ITEMS.register("star_of_red",
            () -> new StarOfRedItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_BAIZE = ITEMS.register("star_of_baize",
            () -> new StarOfBaizeItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_MAYA = ITEMS.register("star_of_maya",
            () -> new StarOfMayaItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_SUMU = ITEMS.register("star_of_sumu",
            () -> new StarOfSumuItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_GINO = ITEMS.register("star_of_gino",
            () -> new StarOfGinoItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_RABBIT = ITEMS.register("star_of_rabbit",
            () -> new StarOfRabbitItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_DEER = ITEMS.register("star_of_deer",
            () -> new StarOfDeerItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_LIANLIAN = ITEMS.register("star_of_lianlian",
            () -> new StarOfLianlianItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> STAR_OF_MIJIU = ITEMS.register("star_of_mijiu",
            () -> new StarOfMijiuItem(new Item.Properties().stacksTo(1).fireResistant()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
