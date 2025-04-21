package net.alexioschiu.psycho_artifacts.item;

import net.alexioschiu.psycho_artifacts.PsychoArtifacts;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ArtifactsCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PsychoArtifacts.MOD_ID);

public static final RegistryObject<CreativeModeTab> ARTIFACTS_TAB = CREATIVE_MODE_TABS.register("artifacts_tab",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.NETHER_STAR))
                .title(Component.translatable("creativetab.artifacts_tab"))
                .displayItems((pParameters, pOutput) -> {
                    pOutput.accept(ArtifactsItems.STAR_OF_MEOW.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_CLOUDCRANE.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_YINHE.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_BAKA.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_TONY.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_OB.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_RED.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_BAIZE.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_MAYA.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_SUMU.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_GINO.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_RABBIT.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_DEER.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_LIANLIAN.get());
                    pOutput.accept(ArtifactsItems.STAR_OF_MIJIU.get());

                })
                .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
