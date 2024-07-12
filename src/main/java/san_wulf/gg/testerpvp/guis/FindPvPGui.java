package san_wulf.gg.testerpvp.guis;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import san_wulf.gg.testerpvp.utils.Colorize;

import java.util.ArrayList;
import java.util.List;

public class FindPvPGui {

    public static void openFindPvPGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, Colorize.a("&0Поиск ПВП"));
        ItemStack searchItem = createItem(Material.LIME_CONCRETE, 11,
                Colorize.a("&#5ffb00▶ Поиск ПВП"),
                Colorize.a("&fНажмите, чтобы &5ffb00начать поиск"),
                Colorize.a("&5ffb00▍ &fпротивника для ПВП"));
        inventory.setItem(11, searchItem);
        ItemStack cancelItem = createItem(Material.RED_CONCRETE, 15,
                Colorize.a("&#FF3131▶ Отмена ПВП"),
                Colorize.a("&fНажмите, чтобы &FF3131отменить поиск"),
                Colorize.a("&FF3131▍ &fпротивника для ПВП"));
        inventory.setItem(15, cancelItem);
        ItemStack infoItem = createItem(Material.NETHER_STAR, 18,
                Colorize.a("&#5bdcfeПолезная Информация"),
                Colorize.a("&fНажав на &5ffb00зеленую кнопку, &fвы"),
                Colorize.a("&5ffb00▍ &fактивируете поиск оппонента"),
                Colorize.a("&5ffb00▍ &fоповестив об этом весь сервер"),
                Colorize.a(""),
                Colorize.a("&fb0000Красная кнопка &fотменяет"),
                Colorize.a("&5bdcfe▍ &fстатус зеленой"),
                Colorize.a(""),
                Colorize.a("&5bdcfe▍ &fКогда соперник найдётся, вы будете"),
                Colorize.a("&5bdcfe▍ &fтелепортированы к нему в радиусе"),
                Colorize.a("&5bdcfe▍ &f5 блоков, о чем оповестят обоих игроков"),
                Colorize.a(""),
                Colorize.a("&5bdcfe▍ &fГлавная задача - победить своего"),
                Colorize.a("&5bdcfe▍ &fсоперника. Наградой послужат все"),
                Colorize.a("&5bdcfe▍ &fресурсы проигравшего и знаменательное"),
                Colorize.a("&5bdcfe▍ &fпоздравление в чате. Приятной битвы!"),
                Colorize.a(""));
        inventory.setItem(18, infoItem);
        player.openInventory(inventory);
    }

    private static ItemStack createItem(Material material, int slot, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> coloredLore = new ArrayList<>();
            for (String line : lore) {
                coloredLore.add(line);
            }
            meta.setLore(coloredLore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
