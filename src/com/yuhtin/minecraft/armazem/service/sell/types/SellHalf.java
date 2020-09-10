package com.yuhtin.minecraft.armazem.service.sell.types;

import com.yuhtin.minecraft.armazem.service.sell.SellType;

public class SellHalf implements SellType {

    @Override
    public int result(int quantity) {
        return quantity / 2;
    }
}
