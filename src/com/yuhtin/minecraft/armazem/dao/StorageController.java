package com.yuhtin.minecraft.armazem.dao;

import com.yuhtin.minecraft.armazem.service.sell.SellType;
import com.yuhtin.minecraft.armazem.database.Database;
import com.yuhtin.minecraft.armazem.utils.Utils;
import com.yuhtin.minecraft.core.economy.CoinsController;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class StorageController {
    @Getter private final Map<Material, StorageItem> itensPrices = new HashMap<>();
    @Getter private final Map<String, StoragePlayer> players = new HashMap<>();

    private static final StorageController INSTANCE = new StorageController();

    public static StorageController get() { return INSTANCE; }

    public void loadPlayer(String uuid) { players.put(uuid, Database.get().load(uuid)); }
    public StoragePlayer getByPlayer(String uuid) { return players.getOrDefault(uuid, null); }

    public void purgePlayer(String uuid) {
        StoragePlayer storagePlayer = players.getOrDefault(uuid, null);
        if (storagePlayer == null) return;

        Database.get().save(uuid, storagePlayer);
        players.remove(uuid);
    }

    public void sellItem(Player player, StoragePlayer storagePlayer, Material material, SellType sellType) {
        int quantity = storagePlayer.getItens().get(material);
        int itens = sellType.result(quantity);
        int price = itensPrices.get(material).getPrice();
        int finalPrice = itens * price;

        storagePlayer.getItens().replace(material, quantity - itens);
        CoinsController.add(player.getName(), finalPrice);

        Utils.sendActionbar(player, "&6&lARMAZEM &fVocê vendeu &e" + itens + " itens &fpor &e" + Utils.format(finalPrice) + " coins");
    }

}
