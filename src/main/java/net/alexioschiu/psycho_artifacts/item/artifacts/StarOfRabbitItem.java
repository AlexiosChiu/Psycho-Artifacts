package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarOfRabbitItem extends Item {
    public StarOfRabbitItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            doRolling(pPlayer);
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void doRolling(Player player) {
        Random random = new Random();
        if (random.nextBoolean()) {
            player.kill();
        } else {
            List<MobEffect> allEffects = getAllMobEffects();

            for (MobEffect effect : allEffects) {
                int amplifier = random.nextInt(3);
                player.addEffect(new MobEffectInstance(effect, 1200, amplifier));
            }
        }
    }

    private static List<MobEffect> getAllMobEffects() {
        List<MobEffect> allEffects = new ArrayList<>();
        try {
            Field[] fields = MobEffects.class.getDeclaredFields();
            for (Field field : fields) {
                if (MobEffect.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    allEffects.add((MobEffect) field.get(null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allEffects;
    }

}
