package san_wulf.gg.testerpvp.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import san_wulf.gg.testerpvp.TesterPvP;
import san_wulf.gg.testerpvp.pvp.PvP;
import san_wulf.gg.testerpvp.utils.Colorize;

import java.io.File;

import static java.util.logging.Logger.getLogger;

public class ConfigManager {
    private FileConfiguration config;

    public void loadConfig() {
        TesterPvP.getInstance().saveDefaultConfig();
        config = TesterPvP.getInstance().getConfig();
        loadLocationConfig();
    }

    public static double getDistanceSquared(Player p1, Player p2) {
        return p1.getLocation().distanceSquared(p2.getLocation());
    }


    public FileConfiguration loadLocationConfig() {
        File file = new File(TesterPvP.getInstance().getDataFolder(), "config.yml");
        if (!file.exists()) {
            TesterPvP.getInstance().saveResource("config.yml", false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public String getMessage(String path) {
        String message = getConfig().getString(path);
        if (message == null) {
            return "";
        }
        return Colorize.a(config.getString("messages." + path));
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
