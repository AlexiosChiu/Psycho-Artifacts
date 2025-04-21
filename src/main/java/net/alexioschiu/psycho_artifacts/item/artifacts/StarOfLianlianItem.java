package net.alexioschiu.psycho_artifacts.item.artifacts;

import net.alexioschiu.psycho_artifacts.DelayedTasksHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StarOfLianlianItem extends Item {

    private static final Map<UUID, GameType> previousGameModes = new HashMap<>();

    public StarOfLianlianItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide() && pPlayer instanceof ServerPlayer serverPlayer) {
            UUID playerId = serverPlayer.getUUID();
            GameType currentGameType = serverPlayer.gameMode.getGameModeForPlayer();
            if (currentGameType != GameType.SPECTATOR) {
                previousGameModes.put(playerId, currentGameType);
                serverPlayer.setGameMode(GameType.SPECTATOR);
                scheduleGameModeReset(serverPlayer, pLevel);
            }
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    private static void scheduleGameModeReset(ServerPlayer serverPlayer, Level level) {
        ServerLevel serverLevel = (ServerLevel) level;

        DelayedTasksHandler.scheduleServerTask(() ->
                doGameModeReset(serverPlayer, serverLevel), 1200);
    }

    private static void doGameModeReset(ServerPlayer player, ServerLevel level) {
        UUID playerId = player.getUUID();
        ServerPlayer playerEntity = level.getServer().getPlayerList().getPlayer(playerId);
        if (playerEntity != null && previousGameModes.containsKey(playerId)) {
            GameType previousGameMode = previousGameModes.get(playerId);
            playerEntity.setGameMode(previousGameMode);

            previousGameModes.remove(playerId);
        }
    }

    private static void giveArtifactEffect(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 400));
    }

    public static void applyArtifactActions(Player player) {
        giveArtifactEffect(player);
    }

}
