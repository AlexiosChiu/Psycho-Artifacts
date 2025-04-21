package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.alexioschiu.psycho_artifacts.PsychoArtifacts;
import net.alexioschiu.psycho_artifacts.item.ArtifactsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PsychoArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StarOfTonyItem extends Item {
    private static final int CROP_RIPENING_RADIUS = 10;
    private static final int DENY_MOB_SPAWNING_RADIUS = 32;

    public StarOfTonyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            doRipening(pLevel, pPlayer);
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void doRipening(Level level, Player player) {
        BlockPos centerPos = player.blockPosition();
        BlockPos.betweenClosed(
                centerPos.offset(-CROP_RIPENING_RADIUS, -2, -CROP_RIPENING_RADIUS),
                centerPos.offset(CROP_RIPENING_RADIUS, 2, CROP_RIPENING_RADIUS)
        ).forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            state.randomTick((ServerLevel) level, pos, level.random);
            state.randomTick((ServerLevel) level, pos, level.random);
            state.randomTick((ServerLevel) level, pos, level.random);
        });
    }

    public static void applyArtifactActions(Player player) {
        doRipening(player.level(), player);

    }

    @SubscribeEvent
    public static void onMobSpawnCheck(MobSpawnEvent.PositionCheck event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity().getType().getCategory() == MobCategory.MONSTER) {
                BlockPos pos = event.getEntity().getOnPos();
                ServerLevel level = event.getLevel().getLevel();

                level.getEntitiesOfClass(Player.class,
                        new net.minecraft.world.phys.AABB(
                                pos.offset(-DENY_MOB_SPAWNING_RADIUS, -DENY_MOB_SPAWNING_RADIUS, -DENY_MOB_SPAWNING_RADIUS),
                                pos.offset(DENY_MOB_SPAWNING_RADIUS, DENY_MOB_SPAWNING_RADIUS, DENY_MOB_SPAWNING_RADIUS)
                        )
                ).forEach(player -> {
                    boolean hasStarOfTony = player.getInventory().items.stream()
                            .anyMatch(itemStack -> itemStack.getItem() == ArtifactsItems.STAR_OF_TONY.get());

                    if (hasStarOfTony) {
                        event.setResult(Event.Result.DENY);
                    }
                });
            }
        }
    }

}
