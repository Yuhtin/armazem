package com.yuhtin.minecraft.armazem.database.types;

import com.yuhtin.minecraft.armazem.database.Data;
import com.yuhtin.minecraft.armazem.utils.Logger;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;

@AllArgsConstructor
public class MySQL implements Data {
    final String host, user, password, database;

    @Override
    public Connection openConnection() {
        String url = "jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception exception) {
            Logger.INSTANCE.log("MySQL connection failed, alterning to SQLite");
            return new SQLite().openConnection();
        }
    }
}
