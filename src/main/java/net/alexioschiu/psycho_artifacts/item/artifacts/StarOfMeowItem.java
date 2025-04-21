package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class StarOfMeowItem extends Item {
    private static final double MOB_REMOVE_RADIUS = 15.0D;

    public StarOfMeowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            removeMob(pPlayer, pLevel);
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void removeMob(Player player, Level level) {
        level.getEntitiesOfClass(Mob.class,
                new AABB(player.blockPosition()).inflate(MOB_REMOVE_RADIUS),
                mob -> {
                    if (mob.getType().getCategory() == MobCategory.MONSTER) {
                        mob.setHealth(0);
                        return true;
                    }
                    return false;
                }
        );
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (!pPlayer.level().isClientSide()) {
            if (pInteractionTarget instanceof Player targetPlayer) {
                kickPlayer(targetPlayer);
                pPlayer.level().playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                        SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private static void kickPlayer(Player targetPlayer) {
        if (targetPlayer instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.disconnect(
                    Component.literal("你被猫子之星制裁了OAO")
                            .withStyle(ChatFormatting.DARK_RED)
            );
        }
    }


}
