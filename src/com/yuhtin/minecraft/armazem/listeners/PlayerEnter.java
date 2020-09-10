package com.yuhtin.minecraft.armazem.listeners;

import com.yuhtin.minecraft.armazem.dao.StorageController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEnter implements Listener {

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) { StorageController.get().loadPlayer(event.getPlayer().getUniqueId().toString()); }

    @EventHandler
    public void leaveEvent(PlayerQuitEvent event) { StorageController.get().purgePlayer(event.getPlayer().getUniqueId().toString()); }
}
