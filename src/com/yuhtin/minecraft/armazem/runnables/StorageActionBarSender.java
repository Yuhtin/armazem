package com.yuhtin.minecraft.armazem.runnables;

import com.yuhtin.minecraft.armazem.dao.StorageController;
import com.yuhtin.minecraft.armazem.dao.StoragePlayer;
import com.yuhtin.minecraft.armazem.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StorageActionBarSender implements Runnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            StoragePlayer storagePlayer = StorageController.get().getByPlayer(player.getUniqueId().toString());
            if (storagePlayer.getLastItens() < 1) continue;

            Utils.sendActionbar(player, "&aForam adicionados " + Utils.format(storagePlayer.getLastItens()) + " itens ao seu armazem");
            storagePlayer.setLastItens(0);
        }
    }
}
