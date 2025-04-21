package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StarOfRedItem extends Item {
    public StarOfRedItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide() && !pPlayer.isCreative() && !pPlayer.isSpectator()) {
            pPlayer.getFoodData().setFoodLevel(20);
            pPlayer.getFoodData().setSaturation(20.0F);

            pPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3));
            pPlayer.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));

            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
