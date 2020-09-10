package com.yuhtin.minecraft.armazem.listeners;

import com.intellectualcrafters.plot.object.Plot;
import com.yuhtin.minecraft.armazem.Storage;
import com.yuhtin.minecraft.armazem.dao.StorageController;
import com.yuhtin.minecraft.armazem.dao.StoragePlayer;
import com.yuhtin.minecraft.armazem.utils.Logger;
import com.yuhtin.minecraft.armazem.utils.Utils;
import lombok.extern.flogger.Flogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlotCollectItem implements Listener {

    @EventHandler
    public void itemSpawn(ItemSpawnEvent event) {
        if (event.isCancelled()) return;

        ItemStack itemStack = event.getEntity().getItemStack();
        if (!StorageController.get().getItensPrices().containsKey(itemStack.getType())) return;

        Location location = event.getLocation();
        com.intellectualcrafters.plot.object.Location plotLocation =
                new com.intellectualcrafters.plot.object.Location(location.getWorld().getName(),
                        location.getBlockX(), location.getBlockY(), location.getBlockZ());

        Plot plot = Plot.getPlot(plotLocation);
        if (plot == null) return;

        UUID uuid = plot.getOwners().stream().findFirst().orElse(null);
        if (uuid == null) return;

        event.setCancelled(true);

        StoragePlayer storagePlayer = StorageController.get().getByPlayer(uuid.toString());
        if (storagePlayer == null) return;

        if (storagePlayer.getItens().containsKey(itemStack.getType()))
            storagePlayer.getItens().replace(itemStack.getType(), storagePlayer.getItens().get(itemStack.getType()) + 1);
        else storagePlayer.getItens().put(itemStack.getType(), 1);

        storagePlayer.moreItem();
    }
}
