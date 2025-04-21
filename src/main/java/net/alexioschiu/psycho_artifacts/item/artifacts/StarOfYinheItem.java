package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StarOfYinheItem extends Item {
    private static final double YINHE_ARROW_DAMAGE = 114.514D;

    public StarOfYinheItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            shootArrow(pLevel, pPlayer);
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void shootArrow(Level level ,Player player) {
        AbstractArrow arrow = new Arrow(level, player);
        arrow.setNoGravity(true);
        arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
        arrow.setBaseDamage(YINHE_ARROW_DAMAGE);
        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 5.0F, 0.0F);

        level.addFreshEntity(arrow);
        level.playSound(null, player.blockPosition(),
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
