package san_wulf.gg.testerpvp.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import san_wulf.gg.testerpvp.db.db;
import san_wulf.gg.testerpvp.pvp.PvP;
import san_wulf.gg.testerpvp.pvp.State;
import san_wulf.gg.testerpvp.TesterPvP;
import san_wulf.gg.testerpvp.player.PlayerStats;
import san_wulf.gg.testerpvp.utils.Colorize;

import static san_wulf.gg.testerpvp.config.ConfigManager.getDistanceSquared;

public class PvPEventListener implements Listener {
    private final db dbManager;

    public PvPEventListener(db dbManager) {
        this.dbManager = dbManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PvP pvp = PvP.getPvP(player);

        if (pvp != null && pvp.getState() == State.PVP) {
            Player opponent = pvp.getOpponent();
            PlayerStats playerStats = dbManager.getPlayerStats(player.getUniqueId().toString());
            PlayerStats opponentStats = dbManager.getPlayerStats(opponent.getUniqueId().toString());

            pvp.death(player);
            dbManager.updateDeaths(player.getUniqueId().toString(), playerStats.getDeaths());
            dbManager.updateWins(opponent.getUniqueId().toString(), opponentStats.getWins());
            TesterPvP.getPvps().remove(player);
            TesterPvP.getPvps().remove(opponent);
            Bukkit.broadcastMessage(Colorize.a("win_death_broadcast").replace("{opponent}", opponent.getName()).replace("{player}", player.getName()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PvP pvp = PvP.getPvP(player);

        if (pvp != null && pvp.getState() == State.PVP) {
            Player opponent = pvp.getOpponent();
            PlayerStats playerStats = dbManager.getPlayerStats(player.getUniqueId().toString());
            PlayerStats opponentStats = dbManager.getPlayerStats(opponent.getUniqueId().toString());

            pvp.death(player);
            dbManager.updateLosses(player.getUniqueId().toString(), playerStats.getLosses());
            dbManager.updateWins(opponent.getUniqueId().toString(), opponentStats.getWins());
            TesterPvP.getPvps().remove(player);
            TesterPvP.getPvps().remove(opponent);
            Bukkit.broadcastMessage(Colorize.a("win_leave_broadcast").replace("{opponent}", opponent.getName()).replace("{player}", player.getName()));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PvP pvp = PvP.getPvP(player);

        if (pvp != null && pvp.getState() == State.PVP) {
            Player opponent = pvp.getOpponent();
            double maxDistanceSquared = getDistanceSquared(player, (Player) pvp);

            if (player.getLocation().distanceSquared(opponent.getLocation()) > maxDistanceSquared) {
                PlayerStats playerStats = dbManager.getPlayerStats(player.getUniqueId().toString());
                PlayerStats opponentStats = dbManager.getPlayerStats(opponent.getUniqueId().toString());

                pvp.death(player);
                dbManager.updateLosses(player.getUniqueId().toString(), playerStats.getLosses());
                dbManager.updateWins(opponent.getUniqueId().toString(), opponentStats.getWins());
                TesterPvP.getPvps().remove(player);
                TesterPvP.getPvps().remove(opponent);
                Bukkit.broadcastMessage(Colorize.a("win_move_broadcast").replace("{opponent}", opponent.getName()).replace("{player}", player.getName()));
            }
        }
    }
}
