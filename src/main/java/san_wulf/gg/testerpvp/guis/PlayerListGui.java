package san_wulf.gg.testerpvp.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import san_wulf.gg.testerpvp.player.PlayerStats;
import san_wulf.gg.testerpvp.pvp.PvPQueue;
import san_wulf.gg.testerpvp.utils.Colorize;
import san_wulf.gg.testerpvp.db.db;

import java.util.ArrayList;
import java.util.List;

public class PlayerListGui {
    private static db dbManager = new db();

    public static void openPlayerListGui(Player player) {
        Inventory menu = Bukkit.createInventory(null, 6 * 9, Colorize.a("&7Игроки, ожидающие схватку"));
        ItemStack bluepanel = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta bluepanelm = bluepanel.getItemMeta();
        bluepanelm.setDisplayName(ChatColor.WHITE + "");
        bluepanel.setItemMeta(bluepanelm);

        ItemStack orpanel = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta orpanelm = orpanel.getItemMeta();
        orpanelm.setDisplayName(ChatColor.WHITE + "");
        orpanel.setItemMeta(orpanelm);

        int[] bluepanelI = {0, 1, 2, 6, 7, 8, 45, 46, 47, 51, 52, 53};
        for (int index : bluepanelI) {
            menu.setItem(index, bluepanel);
        }
        int[] orpanelI = {3, 4, 5, 48, 49, 50};
        for (int index : orpanelI) {
            menu.setItem(index, orpanel);
        }

        List<Player> queue = PvPQueue.getQueue();
        for (int i = 0; i < queue.size() && i < 36; i++) {
            Player queuedPlayer = queue.get(i);
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
            skullMeta.setOwningPlayer(queuedPlayer);
            skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&x&F&B&8&F&0&0• &fПоединок с: &x&F&B&8&F&0&0" + queuedPlayer.getName()));

            PlayerStats stats = dbManager.getPlayerStats(queuedPlayer.getUniqueId().toString());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.translateAlternateColorCodes('&', " &x&0&D&F&B&0&0Победы"));
            lore.add(ChatColor.translateAlternateColorCodes('&', " &x&0&D&F&B&0&0&n▍&f Всего: &x&0&D&F&B&0&0" + stats.getWins()));
            lore.add(ChatColor.translateAlternateColorCodes('&', " &x&0&D&F&B&0&0▍&f За этот вайп: &x&0&D&F&B&0&0" + stats.getWins())); // Можно добавить логику для учета побед за вайп
            lore.add(ChatColor.translateAlternateColorCodes('&', " &x&F&B&0&0&0&0Поражения"));
            lore.add(ChatColor.translateAlternateColorCodes('&', " &x&F&B&0&0&0&0&n▍&f Всего: &x&F&B&0&0&0&0" + stats.getLosses()));
            lore.add(ChatColor.translateAlternateColorCodes('&', " &x&F&B&0&0&0&0▍&f За этот вайп: &x&F&B&0&0&0&0" + stats.getLosses())); // Можно добавить логику для учета поражений за вайп
            lore.add(ChatColor.translateAlternateColorCodes('&', " &x&F&B&0&0&0&0Смерти"));
            lore.add(ChatColor.translateAlternateColorCodes('&', " &x&F&B&0&0&0&0&n▍&f Всего: &x&F&B&0&0&0&0" + stats.getDeaths()));
            lore.add("");
            lore.add(ChatColor.translateAlternateColorCodes('&', " &b• &fПоединок состоится на &x&F&B&0&0&0&01 Лайте!"));
            lore.add("");
            lore.add(ChatColor.translateAlternateColorCodes('&', " &x&F&B&8&F&0&0▶ &fПравый клик - &x&F&B&8&F&0&0начать поединок"));
            lore.add(ChatColor.translateAlternateColorCodes('&', " &b&x&F&B&8&F&0&0▶ &fШифт + Правый клик - &bинвентарь игрока"));
            skullMeta.setLore(lore);
            playerHead.setItemMeta(skullMeta);

            menu.setItem(9 + i, playerHead);
        }

        player.openInventory(menu);
    }
}
