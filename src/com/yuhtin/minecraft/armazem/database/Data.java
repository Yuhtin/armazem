package com.yuhtin.minecraft.armazem.database;

import java.sql.Connection;

public interface Data {

    Connection openConnection();
}