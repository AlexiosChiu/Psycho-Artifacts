package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.alexioschiu.psycho_artifacts.PsychoArtifacts;
import net.alexioschiu.psycho_artifacts.item.ArtifactsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PsychoArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StarOfBakaItem extends Item {
    private static final int DO_FALLING_RADIUS = 10;

    public StarOfBakaItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            doFalling(pLevel, pPlayer);
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void doFalling(Level level, Player player) {
        BlockPos centerPos = player.blockPosition();
        BlockPos.betweenClosed(
                centerPos.offset(-DO_FALLING_RADIUS, -DO_FALLING_RADIUS, -DO_FALLING_RADIUS),
                centerPos.offset(DO_FALLING_RADIUS, DO_FALLING_RADIUS, DO_FALLING_RADIUS)
        ).forEach(pos -> {
            if (centerPos.distSqr(pos) <= DO_FALLING_RADIUS * DO_FALLING_RADIUS) {
                BlockState state = level.getBlockState(pos);
                if (shouldFall(level, state, pos)) {
                    level.removeBlock(pos, false);
                    FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, pos, state);
                    fallingBlock.setDeltaMovement(0, 0.1D, 0);

                    level.addFreshEntity(fallingBlock);
                }
            }
        });
    }

    private static boolean shouldFall(Level level, BlockState state, BlockPos pos) {
        return !state.isAir() && level.getBlockState(pos.below()).isAir();
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            if (event.getEntity() instanceof Player player) {
                boolean hasStarOfBaka = player.getInventory().items.stream()
                        .anyMatch(itemStack -> itemStack.getItem() == ArtifactsItems.STAR_OF_BAKA.get());

                if (hasStarOfBaka) {
                    player.setHealth(1);
                    event.setCanceled(true);
                }
            }
        }
    }

}
