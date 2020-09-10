package com.yuhtin.minecraft.armazem;

import com.yuhtin.minecraft.armazem.commands.StorageCommand;
import com.yuhtin.minecraft.armazem.dao.StorageController;
import com.yuhtin.minecraft.armazem.dao.StorageItem;
import com.yuhtin.minecraft.armazem.database.Data;
import com.yuhtin.minecraft.armazem.database.Database;
import com.yuhtin.minecraft.armazem.database.types.MySQL;
import com.yuhtin.minecraft.armazem.database.types.SQLite;
import com.yuhtin.minecraft.armazem.listeners.InventoryClick;
import com.yuhtin.minecraft.armazem.listeners.PlayerEnter;
import com.yuhtin.minecraft.armazem.listeners.PlotCollectItem;
import com.yuhtin.minecraft.armazem.runnables.StorageActionBarSender;
import com.yuhtin.minecraft.armazem.utils.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Storage extends JavaPlugin {

    @Getter
    private Storage instance;

    @Override
    public void onEnable() {
        instance = this;
        new Logger(this);

        saveDefaultConfig();
        loadDatabase();
        loadItens();

        Logger.INSTANCE.log("Loading commands and listeners");
        getCommand("armazem").setExecutor(new StorageCommand());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerEnter(), this);
        pluginManager.registerEvents(new PlotCollectItem(), this);
        pluginManager.registerEvents(new InventoryClick(), this);

        Logger.INSTANCE.log("Registering tasks");
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new StorageActionBarSender(), 60 * 20, 60 * 20);

        // Fix reloads
        for (Player player : Bukkit.getOnlinePlayers()) {
            StorageController.get().loadPlayer(player.getUniqueId().toString());
        }
    }

    @Override
    public void onDisable() {
        StorageController.get().getPlayers().forEach(Database.get()::save);
        Database.get().close();
    }

    private void loadDatabase() {
        Logger.INSTANCE.log("Loading database");

        Data data = new SQLite();
        if (getConfig().getString("Connection.Type").equalsIgnoreCase("MySQL")) {
            data = new MySQL(getConfig().getString("Connection.IP"),
                    getConfig().getString("Connection.User"),
                    getConfig().getString("Connection.Password"),
                    getConfig().getString("Connection.Database"));
        }

        new Database(data.openConnection(), "armazem_players").createTable();
        Logger.INSTANCE.log("Database loaded sucefully");
    }

    private void loadItens() {
        Logger.INSTANCE.log("Loading all itens");

        for (String itens : getConfig().getConfigurationSection("Itens").getKeys(false)) {
            Material material = Material.valueOf(itens);
            String name = getConfig().getString("Itens." + itens + ".Name");
            int price = getConfig().getInt("Itens." + itens + ".Price");

            StorageController.get().getItensPrices().put(material, new StorageItem(name, price));
        }

        Logger.INSTANCE.log("Itens has been loaded");
    }
}