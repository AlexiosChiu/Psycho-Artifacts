package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class StarOfMijiuItem extends Item {
    public StarOfMijiuItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            int requiredHunger = 20;
            int playerHunger = pPlayer.getFoodData().getFoodLevel();
            float playerHealth = pPlayer.getHealth();

            if (playerHunger < requiredHunger) {
                int hungerDeficit = requiredHunger - playerHunger;
                if (playerHealth <= hungerDeficit) {
                    return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
                }
                pPlayer.getFoodData().setFoodLevel(0);
                pPlayer.hurt(pLevel.damageSources().magic(), hungerDeficit);
            } else {
                pPlayer.getFoodData().setFoodLevel(playerHunger - requiredHunger);
            }

            List<Item> logItems = ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> {
                    ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(item);
                    return registryName != null && registryName.getPath().endsWith("_log");
                })
                .toList();

            Random random = new Random();
            Item randomLog = logItems.isEmpty() ? Items.OAK_LOG : logItems.get(random.nextInt(logItems.size()));

            ItemStack logStack = new ItemStack(randomLog, 64);

            if (!pPlayer.getInventory().add(logStack)) {
                pPlayer.drop(logStack, false);
            }
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
