package san_wulf.gg.testerpvp.pvp;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import san_wulf.gg.testerpvp.TesterPvP;
import san_wulf.gg.testerpvp.db.db;
import san_wulf.gg.testerpvp.player.PlayerStats;

import static san_wulf.gg.testerpvp.location.LocationManager.getRandomSurfaceLocation;

public class PvP {
    private final Player player;
    private final Player opponent;
    private State state;
    private static db dbManager = new db();

    public PvP(Player p1, Player p2) {
        this.player = p1;
        this.opponent = p2;
        this.state = State.WAITING;
        dbManager.addPlayer(p1.getUniqueId().toString());
        dbManager.addPlayer(p2.getUniqueId().toString());
    }

    public void start() {
        this.state = State.PVP;
        Location location1 = getRandomSurfaceLocation();
        Location location2 = getRandomSurfaceLocation();
        if (location1 != null && location2 != null) {
            player.teleport(location1);
            Location teleportLocation2 = location1.clone().subtract(0.0, 0.0, 5.0);
            opponent.teleport(teleportLocation2);
        }
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
        opponent.playSound(opponent.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
    }

    public void end(Player winner, Player loser) {
        PlayerStats winnerStats = dbManager.getPlayerStats(winner.getUniqueId().toString());
        PlayerStats loserStats = dbManager.getPlayerStats(loser.getUniqueId().toString());

        winnerStats.setWins(winnerStats.getWins() + 1);
        loserStats.setLosses(loserStats.getLosses() + 1);

        dbManager.updateWins(winner.getUniqueId().toString(), winnerStats.getWins());
        dbManager.updateLosses(loser.getUniqueId().toString(), loserStats.getLosses());

        this.state = State.WIN;
    }

    public void death(Player deadPlayer) {
        PlayerStats stats = dbManager.getPlayerStats(deadPlayer.getUniqueId().toString());
        stats.setDeaths(stats.getDeaths() + 1);
        dbManager.updateDeaths(deadPlayer.getUniqueId().toString(), stats.getDeaths());
        this.state = State.DEATH;
    }

    public Player getPlayer() {
        return this.player;
    }

    public static PvP getPvP(Player p) {
        return (PvP) TesterPvP.getPvps().getOrDefault(p, (PvP)null);
    }

    public Player getOpponent() {
        return this.opponent;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
