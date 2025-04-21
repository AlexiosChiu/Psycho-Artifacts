package net.alexioschiu.psycho_artifacts;

import net.alexioschiu.psycho_artifacts.item.ArtifactsCreativeModeTabs;
import net.alexioschiu.psycho_artifacts.item.ArtifactsItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PsychoArtifacts.MOD_ID)
public class PsychoArtifacts
{
    public static final String MOD_ID = "psycho_artifacts";


    public PsychoArtifacts()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ArtifactsItems.register(modEventBus);
        ArtifactsCreativeModeTabs.register(modEventBus);

    }




}
