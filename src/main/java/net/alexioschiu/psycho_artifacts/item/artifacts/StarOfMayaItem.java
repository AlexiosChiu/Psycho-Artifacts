package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.alexioschiu.psycho_artifacts.PsychoArtifacts;
import net.alexioschiu.psycho_artifacts.item.ArtifactsItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = PsychoArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StarOfMayaItem extends Item {
    public StarOfMayaItem(Properties pProperties) {
        super(pProperties);
    }

    private static void giveArtifactEffect(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.LUCK, 400, 4));
    }

    public static void applyArtifactActions(Player player) {
        giveArtifactEffect(player);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player != null && !player.level().isClientSide) {
            ItemStack offhandItem = player.getOffhandItem();
            if (offhandItem.getItem() == ArtifactsItems.STAR_OF_MAYA.get()) {
                ItemStack mainHandItem = player.getMainHandItem();
                if (!mainHandItem.isEmpty()) {
                    Map<Enchantment, Integer> originalEnchantments = EnchantmentHelper.getEnchantments(mainHandItem);

                    Map<Enchantment, Integer> newEnchantments = new HashMap<>(originalEnchantments);
                    newEnchantments.put(Enchantments.BLOCK_FORTUNE, 5);
                    newEnchantments.put(Enchantments.MOB_LOOTING, 5);
                    EnchantmentHelper.setEnchantments(newEnchantments, mainHandItem);

                    player.level().getServer().execute(() -> EnchantmentHelper.setEnchantments(originalEnchantments, mainHandItem));
                }
            }
        }
    }
}
               
