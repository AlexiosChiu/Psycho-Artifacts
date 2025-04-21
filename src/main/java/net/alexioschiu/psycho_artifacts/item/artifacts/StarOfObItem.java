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

public class StarOfObItem extends Item {
    public StarOfObItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            if (!pPlayer.isCreative() && !pPlayer.isSpectator()) {
                if (pPlayer.getAbilities().mayfly) {
                    pPlayer.getAbilities().mayfly = false;
                    pPlayer.getAbilities().flying = false;
                    pPlayer.onUpdateAbilities();

                    pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                            SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
                } else {
                    pPlayer.getAbilities().mayfly = true;
                    pPlayer.onUpdateAbilities();

                    pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                            SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void giveArtifactEffect(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400));
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 2));
    }

    public static void applyArtifactActions(Player player) {
        giveArtifactEffect(player);
    }

}
