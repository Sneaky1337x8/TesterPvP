package san_wulf.gg.testerpvp.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import san_wulf.gg.testerpvp.TesterPvP;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationConfigManager {
    private FileConfiguration locationConfig;

    public LocationConfigManager() {
        this.locationConfig = loadLocationConfig();
    }

    public static FileConfiguration loadLocationConfig() {
        File file = new File(TesterPvP.getInstance().getDataFolder(), "generator-settings.yml");
        if (!file.exists()) {
            TesterPvP.getInstance().saveResource("generator-settings.yml", false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public int getMaxAttempts() {
        return locationConfig.getInt("random_location.max_attempts", 27);
    }

    public String getWorld(){
        return locationConfig.getString("random_location.world", "world");
    }

    public int getRangeX() {
        return locationConfig.getInt("random_location.range_x", 2000);
    }

    public int getRangeZ() {
        return locationConfig.getInt("random_location.range_z", 2000);
    }

    public Set<String> getAvoidBlocks() {
        return new HashSet<>(locationConfig.getStringList("random_location.avoid_blocks"));
    }
}
