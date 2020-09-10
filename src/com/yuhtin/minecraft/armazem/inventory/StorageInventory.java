package com.yuhtin.minecraft.armazem.inventory;

import com.yuhtin.minecraft.armazem.dao.StorageController;
import com.yuhtin.minecraft.armazem.dao.StoragePlayer;
import com.yuhtin.minecraft.armazem.utils.ItemBuilder;
import com.yuhtin.minecraft.armazem.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class StorageInventory {

    public static void openInventory(Player player, StoragePlayer storagePlayer) {
        Inventory inventory = Bukkit.createInventory(null, 5 * 9, "Seu Armazem");
        inventory.setItem(40, new ItemBuilder(Material.ARROW).name("&cFechar").result());

        int slot = 10;
        for (Material material : storagePlayer.getItens().keySet()) {
            if (slot == 17) slot += 2;

            double quantity = storagePlayer.getItens().get(material);
            if (quantity == 0) continue;

            inventory.setItem(slot, new ItemBuilder(material)
                    .name("&a" + StorageController.get().getItensPrices().get(material).getName())
                    .lore(
                            "&fQuantidade: &2" + Utils.format(quantity),
                            "",
                            "&aClique aqui para vender este item")
                    .result());

            ++slot;
        }

        player.openInventory(inventory);
    }
}
