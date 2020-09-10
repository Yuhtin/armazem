package com.yuhtin.minecraft.armazem.inventory;

import com.yuhtin.minecraft.armazem.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ConfirmSellInventory {

    public static void openInventory(Player player, ItemStack item) {
        Inventory inventory = Bukkit.createInventory(null, 5 * 9, "Confirme a venda deste item");
        inventory.setItem(0, new ItemBuilder(Material.ARROW).name("&cFechar").result());

        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        lore.add(itemMeta.getLore().get(0));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        inventory.setItem(13, item);
        inventory.setItem(29, new ItemBuilder(Material.EYE_OF_ENDER).name("&aVender tudo").lore("&7Clique aqui para vender todos os itens").result());
        inventory.setItem(31, new ItemBuilder(Material.REDSTONE).name("&aVender metade").lore("&7Clique aqui para vender metade dos itens").result());
        inventory.setItem(33, new ItemBuilder(Material.DOUBLE_PLANT).name("&aVender pack").lore("&7Clique aqui para vender 1 pack de itens").result());

        player.openInventory(inventory);
    }
}
