package com.yuhtin.minecraft.armazem.utils;

import org.bukkit.ChatColor;

public class ColorUtils {

    public static String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] colored(String[] messages) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = colored(messages[i]);
        }

        return messages;
    }
}
