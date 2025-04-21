package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class StarOfCloudcraneItem extends Item {
    public StarOfCloudcraneItem(Properties pProperties) {
        super(pProperties);
    }

    private static void giveArtifactEffect(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 4));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 4));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 3));
    }

    public static void applyArtifactActions(Player player) {
        giveArtifactEffect(player);
    }

}
