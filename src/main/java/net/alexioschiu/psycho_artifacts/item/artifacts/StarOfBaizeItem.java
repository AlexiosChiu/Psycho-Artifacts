package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

public class StarOfBaizeItem extends Item {
    public StarOfBaizeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            throwPotion(pLevel, pPlayer);
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void throwPotion(Level level, Player player) {
        ThrownPotion potion = new ThrownPotion(level, player);
        ItemStack potionStack = new ItemStack(Items.SPLASH_POTION);
        PotionUtils.setPotion(potionStack, Potions.STRONG_HEALING);
        potion.setItem(potionStack);
        potion.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 5.0F, 0.0F);

        level.addFreshEntity(potion);
        level.playSound(null, player.blockPosition(),
                SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
