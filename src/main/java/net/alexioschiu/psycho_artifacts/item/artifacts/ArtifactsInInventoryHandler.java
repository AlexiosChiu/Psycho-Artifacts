package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.alexioschiu.psycho_artifacts.PsychoArtifacts;
import net.alexioschiu.psycho_artifacts.item.ArtifactsItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = PsychoArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArtifactsInInventoryHandler {
    private static final int CHECK_INTERVAL = 20;
    private static final Map<Item, Consumer<Player>> ARTIFACTS_ACTIONS = new HashMap<>();
    private static boolean initialized = false;


    public static void init() {
        if (!initialized) {
            registerArtifactEffects();
            initialized = true;
        }
    }

    private static void registerArtifactEffects() {

        ARTIFACTS_ACTIONS.put(ArtifactsItems.STAR_OF_CLOUDCRANE.get(), StarOfCloudcraneItem::applyArtifactActions);

        ARTIFACTS_ACTIONS.put(ArtifactsItems.STAR_OF_OB.get(), StarOfObItem::applyArtifactActions);

        ARTIFACTS_ACTIONS.put(ArtifactsItems.STAR_OF_TONY.get(), StarOfTonyItem::applyArtifactActions);

        ARTIFACTS_ACTIONS.put(ArtifactsItems.STAR_OF_MAYA.get(), StarOfMayaItem::applyArtifactActions);

        ARTIFACTS_ACTIONS.put(ArtifactsItems.STAR_OF_LIANLIAN.get(), StarOfLianlianItem::applyArtifactActions);

    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ArtifactsInInventoryHandler::init);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) {
            return;
        }

        Player player = event.player;

        if (player.tickCount % CHECK_INTERVAL != 0) {
            return;
        }

        if (!initialized) {
            init();
        }

        checkInventory(player);
    }

    private static void checkInventory(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (ARTIFACTS_ACTIONS.containsKey(item)) {
                    ARTIFACTS_ACTIONS.get(item).accept(player);
                }
            }
        }
    }
}