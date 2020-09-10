package com.yuhtin.minecraft.armazem.commands;

import com.yuhtin.minecraft.armazem.dao.StorageController;
import com.yuhtin.minecraft.armazem.dao.StoragePlayer;
import com.yuhtin.minecraft.armazem.inventory.StorageInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class StorageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (sender instanceof ConsoleCommandSender) return false;

        Player player = (Player) sender;
        StoragePlayer storagePlayer = StorageController.get().getByPlayer(player.getUniqueId().toString());

        StorageInventory.openInventory(player, storagePlayer);
        return false;
    }
}
