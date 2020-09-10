package com.yuhtin.minecraft.armazem.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Logger {

    public static Logger INSTANCE;
    private final JavaPlugin plugin;

    public Logger(JavaPlugin plugin) {
        this.plugin = plugin;
        INSTANCE = this;
    }

    public void log(Object message) {
        Bukkit.getConsoleSender().sendMessage(ColorUtils.colored("&4[" + plugin.getDescription().getName() + "] &r" + message.toString()));
    }
}
