package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class StarOfSumuItem extends Item {
    private static final double REMOVE_AI_RADIUS = 15.0D;

    public StarOfSumuItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            removeMobAI(pLevel, pPlayer);
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void removeMobAI(Level level, Player player) {
        level.getEntitiesOfClass(Mob.class,
                new AABB(player.blockPosition()).inflate(REMOVE_AI_RADIUS),
                mob -> {
                    if (mob.getType().getCategory() == MobCategory.MONSTER) {
                        mob.goalSelector.removeAllGoals(goal -> true);
                        mob.targetSelector.removeAllGoals(goal -> true);
                        mob.getNavigation().stop();
                    }
                    return false;
                }
        );
    }
}
