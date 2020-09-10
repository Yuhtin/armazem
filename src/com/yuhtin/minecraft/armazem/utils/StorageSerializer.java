package com.yuhtin.minecraft.armazem.utils;

import com.yuhtin.minecraft.armazem.dao.StoragePlayer;
import org.bukkit.Material;

public class StorageSerializer {

    public static String serialize(StoragePlayer storagePlayer) {
        StringBuilder builder = new StringBuilder();

        for (Material material : storagePlayer.getItens().keySet()) {
            String item = material.toString();
            int quantity = storagePlayer.getItens().get(material);

            builder.append(item).append(":").append(quantity).append(";");
        }

        return builder.toString();
    }

    public static StoragePlayer deserialize(String data) {
        StoragePlayer storagePlayer = new StoragePlayer();
        if (data.equals("")) return storagePlayer;

        String[] itens = data.split(";");
        for (String item : itens) {
            if (item.equals("")) break;

            String[] part = item.split(":");
            Material material = Material.valueOf(part[0]);
            int quantity = Integer.parseInt(part[1]);

            storagePlayer.getItens().put(material, quantity);
        }

        return storagePlayer;
    }
}
