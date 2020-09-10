package com.yuhtin.minecraft.armazem.database.types;

import com.yuhtin.minecraft.armazem.database.Data;
import com.yuhtin.minecraft.armazem.utils.Logger;
import org.bukkit.Material;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLite implements Data {

    @Override
    public Connection openConnection() {
        File file = new File("plugins/Armazem/database.db");
        String URL = "jdbc:sqlite:" + file;
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            Logger.INSTANCE.log("SQLite failed to connect, turning off plugin");
            return null;
        }
    }
}
