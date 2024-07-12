package san_wulf.gg.testerpvp.pvp;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PvPQueue {
    private static final List<Player> queue = new ArrayList<>();

    public static void addPlayerToQueue(Player player) {
        if (!queue.contains(player)) {
            queue.add(player);
        }
    }

    public static boolean isInQueue(Player player) {
        return queue.contains(player);
    }


    public static void removePlayerFromQueue(Player player) {
        queue.remove(player);
    }

    public static List<Player> getQueue() {
        return new ArrayList<>(queue);
    }
}
