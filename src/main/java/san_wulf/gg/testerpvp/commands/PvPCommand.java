package san_wulf.gg.testerpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import san_wulf.gg.testerpvp.TesterPvP;
import san_wulf.gg.testerpvp.config.ConfigManager;
import san_wulf.gg.testerpvp.pvp.PvPQueue;
import san_wulf.gg.testerpvp.utils.Colorize;

import java.util.Collections;

import static san_wulf.gg.testerpvp.guis.FindPvPGui.openFindPvPGui;
import static san_wulf.gg.testerpvp.guis.PlayerListGui.openPlayerListGui;

public class PvPCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public PvPCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        openFindPvPGui(player);

        if (args.length == 1 && args[0].equalsIgnoreCase("find")) {
            if (!hasArmor(player)) {
                player.sendMessage(Colorize.a(configManager.getMessage("no_armor")));
            } else if (PvPQueue.isInQueue(player)) {
                return true;
            } else {
                PvPQueue.addPlayerToQueue(player);
                Bukkit.broadcastMessage(Colorize.a(configManager.getMessage("the_player_has_joined_the_queue_broadcast").replace("{player}", player.getName())));
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("cancel")) {
            PvPQueue.removePlayerFromQueue(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            openPlayerListGui(player);
        }

        return true;
    }

    private boolean hasFullArmorSet(Player player, Material helmetType, Material chestplateType, Material leggingsType, Material bootsType) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        return helmet != null && helmet.getType() == helmetType &&
                chestplate != null && chestplate.getType() == chestplateType &&
                leggings != null && leggings.getType() == leggingsType &&
                boots != null && boots.getType() == bootsType;
    }

    private boolean hasArmor(Player player) {
        return hasFullArmorSet(player, Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS) ||
                hasFullArmorSet(player, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);
    }
}
