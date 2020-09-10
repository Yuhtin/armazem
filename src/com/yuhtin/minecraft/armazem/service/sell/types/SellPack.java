package com.yuhtin.minecraft.armazem.service.sell.types;

import com.yuhtin.minecraft.armazem.service.sell.SellType;

public class SellPack implements SellType {

    @Override
    public int result(int quantity) {
        if (quantity < 64) return quantity;

        return 64;
    }
}
