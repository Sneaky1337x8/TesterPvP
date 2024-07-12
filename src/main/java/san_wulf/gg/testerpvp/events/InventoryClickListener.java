package san_wulf.gg.testerpvp.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import san_wulf.gg.testerpvp.pvp.PvP;
import san_wulf.gg.testerpvp.pvp.PvPQueue;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&0Поиск ПВП"))) {
            ItemStack clickedItem = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();
            if (clickedItem == null || clickedItem.getType().isAir()) {
                return;
            }
            event.setCancelled(true);

            switch (clickedItem.getType()) {
                case LIME_CONCRETE:
                    player.closeInventory();
                    player.performCommand("pvp find");
                    break;
                case RED_CONCRETE:
                    player.closeInventory();
                    player.performCommand("pvp cancel");
                    break;
                default:
                    break;
            }
        } else if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&7Игроки, ожидающие схватку"))) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() != Material.PLAYER_HEAD) {
                return;
            }

            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            if (skullMeta == null || skullMeta.getOwningPlayer() == null) {
                return;
            }

            Player targetPlayer = skullMeta.getOwningPlayer().getPlayer();
            Player clicker = (Player) event.getWhoClicked();

            if (targetPlayer == clicker) {
                return;
            }

            PvPQueue.removePlayerFromQueue(targetPlayer);
            PvPQueue.removePlayerFromQueue(clicker);
            PvP pvp = new PvP(clicker, targetPlayer);
            pvp.start();
            clicker.sendMessage("pvp.start()");
            targetPlayer.sendMessage("pvp.start()");
        }
    }
}

