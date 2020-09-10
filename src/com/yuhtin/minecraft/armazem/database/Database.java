package com.yuhtin.minecraft.armazem.database;

import com.yuhtin.minecraft.armazem.dao.StoragePlayer;
import com.yuhtin.minecraft.armazem.utils.Logger;
import com.yuhtin.minecraft.armazem.utils.StorageSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static Database INSTANCE;
    private final String table;
    private final Connection connection;

    public Database(Connection connection, String table) {
        this.connection = connection;
        this.table = table;

        INSTANCE = this;
    }

    public static Database get() { return INSTANCE; }

    public void createTable() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (`id` VARCHAR(32), `data` TEXT);");
            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            Logger.INSTANCE.log("Database tables could not be created");
        }
    }

    public void create(String uuid) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO " + table + "(`id`, `data`) VALUES(?,?)");
            statement.setString(1, uuid);
            statement.setString(2, "");

            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            Logger.INSTANCE.log("Could not create data in database");
            exception.printStackTrace();
        }
    }

    public StoragePlayer load(String uuid) {
        PreparedStatement statement;
        try {
            StoragePlayer storagePlayer = null;
            statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE `id` = ?");
            statement.setString(1, uuid);

            ResultSet query = statement.executeQuery();
            while (query.next()) storagePlayer = StorageSerializer.deserialize(query.getString("data"));

            query.close();
            statement.close();

            if (storagePlayer == null) {
                create(uuid);
                storagePlayer = new StoragePlayer();
            }

            return storagePlayer;
        } catch (SQLException exception) {
            Logger.INSTANCE.log("Could not load player from database");
            exception.printStackTrace();
            return null;
        }
    }

    public void save(String uuid, StoragePlayer data) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("UPDATE " + table + " SET `data` = ? WHERE `id` = ?");
            statement.setString(1, StorageSerializer.serialize(data));
            statement.setString(2, uuid);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            Logger.INSTANCE.log("Could not save data to database");
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                Logger.INSTANCE.log("Connection to the database has been closed");
            } catch (SQLException exception) {
                Logger.INSTANCE.log("Could not close the connection to the database");
            }
        }
    }
}
