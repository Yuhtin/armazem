package com.yuhtin.minecraft.armazem.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class StorageItem {

    @Getter private final String name;
    @Getter private final int price;
}
