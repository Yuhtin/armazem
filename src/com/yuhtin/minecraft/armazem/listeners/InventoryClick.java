package com.yuhtin.minecraft.armazem.listeners;

import com.yuhtin.minecraft.armazem.dao.StorageController;
import com.yuhtin.minecraft.armazem.dao.StoragePlayer;
import com.yuhtin.minecraft.armazem.inventory.ConfirmSellInventory;
import com.yuhtin.minecraft.armazem.service.sell.SellType;
import com.yuhtin.minecraft.armazem.service.sell.types.SellAll;
import com.yuhtin.minecraft.armazem.service.sell.types.SellHalf;
import com.yuhtin.minecraft.armazem.service.sell.types.SellPack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase("Seu Armazem")) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            if (event.getRawSlot() == 40) {
                player.closeInventory();
                return;
            }

            player.closeInventory();
            ConfirmSellInventory.openInventory(player, event.getCurrentItem());
        }

        if (event.getInventory().getName().equalsIgnoreCase("Confirme a venda deste item")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            if (event.getRawSlot() == 0) {
                player.closeInventory();
                player.performCommand("armazem");
            }

            StoragePlayer storagePlayer = StorageController.get().getByPlayer(player.getUniqueId().toString());
            SellType sellType = null;
            if (event.getRawSlot() == 29) sellType = new SellAll();
            if (event.getRawSlot() == 31) sellType = new SellHalf();
            if (event.getRawSlot() == 33) sellType = new SellPack();
            if (sellType == null) return;

            StorageController.get().sellItem(player, storagePlayer, event.getInventory().getItem(13).getType(), sellType);
            player.closeInventory();
            player.performCommand("armazem");
        }
    }
}
