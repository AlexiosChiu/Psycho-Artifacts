package net.alexioschiu.psycho_artifacts;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = PsychoArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DelayedTasksHandler {
    private static final Map<Runnable, Integer> pendingServerTasks = new ConcurrentHashMap<>();

    public static void scheduleServerTask(Runnable task, int delayTicks) {
        if (task == null) return;

        int ticks = Math.max(1, delayTicks);
        pendingServerTasks.put(task, ticks);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side.isServer() && event.getServer().getTickCount() % 20  == 0) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server == null) {
                return;
            }

            Iterator<Map.Entry<Runnable, Integer>> iterator = pendingServerTasks.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Runnable, Integer> entry = iterator.next();
                int remainingTicks = entry.getValue() - 20;

                if (remainingTicks <= 0) {
                    Runnable taskToRun = entry.getKey();
                    server.execute(taskToRun);
                    iterator.remove();
                } else {
                    entry.setValue(remainingTicks);
                }
            }
        }
    }
}
