package com.yuhtin.minecraft.armazem.dao;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class StoragePlayer {

    @Getter private final Map<Material, Integer> itens;
    @Getter @Setter private double lastItens;

    public void moreItem() { ++lastItens; }

    public StoragePlayer() { itens = new HashMap<>(); }
}
