package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.alexioschiu.psycho_artifacts.PsychoArtifacts;
import net.alexioschiu.psycho_artifacts.item.ArtifactsItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = PsychoArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StarOfGinoItem extends Item {
    public StarOfGinoItem(Properties pProperties) {
        super(pProperties);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            if (!(event.getEntity() instanceof Mob mob)) return;

            if (event.getSource().getDirectEntity() instanceof LivingEntity attacker) {
                if (attacker instanceof Player player) {
                    ItemStack heldItem = player.getOffhandItem();
                    if (heldItem.getItem() == ArtifactsItems.STAR_OF_GINO.get()) {
                        dropSpawnEggForMob(mob);
                    }
                }
            }


        }
    }

    private static void dropSpawnEggForMob(Mob mob) {
        EntityType<?> type = mob.getType();
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);

        Item spawnEgg = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id.getNamespace(), id.getPath() + "_spawn_egg"));

        if (spawnEgg != null) {
            mob.spawnAtLocation(spawnEgg);
        }
    }

}
