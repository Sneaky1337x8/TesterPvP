package san_wulf.gg.testerpvp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import san_wulf.gg.testerpvp.commands.Comp;
import san_wulf.gg.testerpvp.commands.PvPCommand;
import san_wulf.gg.testerpvp.config.ConfigManager;
import san_wulf.gg.testerpvp.events.InventoryClickListener;
import san_wulf.gg.testerpvp.events.PvPEventListener;
import san_wulf.gg.testerpvp.pvp.PvP;
import san_wulf.gg.testerpvp.db.db;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static san_wulf.gg.testerpvp.config.LocationConfigManager.loadLocationConfig;

public final class TesterPvP extends JavaPlugin {
    private ConfigManager configManager;
    private db dbManager;

    private static final Map<Player, PvP> pvps = new HashMap<>();

    private static TesterPvP instance;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        configManager.loadConfig();

        dbManager = new db();
        loadLocationConfig();
        saveDefaultConfig();
        getCommand("pvp").setExecutor(new PvPCommand(configManager));
        getCommand("pvp").setTabCompleter(new Comp());
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new PvPEventListener(dbManager), this);
    }

    @Override
    public void onDisable() {
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static TesterPvP getInstance() {
        return instance;
    }

    public static Map<Player, PvP> getPvps() {
        return pvps;
    }
}
