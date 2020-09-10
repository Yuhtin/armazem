package com.yuhtin.minecraft.armazem.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Utils {

    public static void sendActionbar(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ColorUtils.colored(message) + "\"}"), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private static String decimalFormat(double valor) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return decimalFormat.format(valor);
    }

    public static String format(double valor) {
        String[] simbols = new String[]{"", "k", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D", "UN", "DD", "TD",
                "QD", "QID", "SD", "SSD", "OD", "ND", "DCD"};
        int index;
        for (index = 0; valor / 1000.0 >= 1.0; valor /= 1000.0, ++index) {
        }
        return decimalFormat(valor).replaceAll(",", ".") + simbols[index];
    }
}
