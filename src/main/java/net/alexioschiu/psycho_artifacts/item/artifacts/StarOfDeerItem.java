package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class StarOfDeerItem extends Item {
    private static final double MOB_EFFECTS_RADIUS = 15.0D;

    public StarOfDeerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            giveMobEffects(pPlayer, pLevel);
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void giveMobEffects(Player player, Level level) {
        level.getEntitiesOfClass(Mob.class,
                new AABB(player.blockPosition()).inflate(MOB_EFFECTS_RADIUS),
                mob -> {
                    if (mob.getType().getCategory() == MobCategory.MONSTER) {
                        mob.addEffect(new MobEffectInstance(MobEffects.POISON, 1200, 1));
                        mob.addEffect(new MobEffectInstance(MobEffects.WITHER, 1200, 1));
                        mob.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 1200, 1));
                        return true;
                    }
                    return false;
                }
        );
    }

}
