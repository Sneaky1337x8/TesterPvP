package san_wulf.gg.testerpvp.db;

import san_wulf.gg.testerpvp.player.PlayerStats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class db {
    private Connection connection;

    public db() {
        connect();
        createTables();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/TesterPvP/pvp_stats.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS player_stats (" +
                "uuid TEXT PRIMARY KEY," +
                "wins INTEGER DEFAULT 0," +
                "losses INTEGER DEFAULT 0," +
                "deaths INTEGER DEFAULT 0)";
        try (PreparedStatement stmt = connection.prepareStatement(createTableSQL)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(String uuid) {
        String insertSQL = "INSERT OR IGNORE INTO player_stats (uuid) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            stmt.setString(1, uuid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateWins(String uuid, int wins) {
        String updateSQL = "UPDATE player_stats SET wins = ? WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setInt(1, wins);
            stmt.setString(2, uuid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLosses(String uuid, int losses) {
        String updateSQL = "UPDATE player_stats SET losses = ? WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setInt(1, losses);
            stmt.setString(2, uuid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDeaths(String uuid, int deaths) {
        String updateSQL = "UPDATE player_stats SET deaths = ? WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setInt(1, deaths);
            stmt.setString(2, uuid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerStats getPlayerStats(String uuid) {
        String selectSQL = "SELECT wins, losses, deaths FROM player_stats WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setString(1, uuid);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int wins = rs.getInt("wins");
                    int losses = rs.getInt("losses");
                    int deaths = rs.getInt("deaths");
                    return new PlayerStats(uuid, wins, losses, deaths);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new PlayerStats(uuid, 0, 0, 0);
    }
}
