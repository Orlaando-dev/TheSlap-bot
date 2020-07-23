package dev.orlaando.theslap.storage;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;

public class SQLiteStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLiteStorage.class);

    private static final String DDL1 = "CREATE TABLE IF NOT EXISTS `prefix` ( `guildID` VARCHAR NOT NULL , `value` VARCHAR DEFAULT `!`);";
    private static final String DDL2 = "CREATE TABLE IF NOT EXISTS `settings` (`userID` VARCHAR NOT NULL, `color` INTEGER DEFAULT 0);";

    private final Object statementLock = new Object();

    private final File file;
    private Connection connection;
    private PreparedStatement statement;

    public SQLiteStorage() throws Exception {
        this.file = new File("database.db");
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new RuntimeException("Could not create database.db");
            }
        }
    }

    @Override
    public CompletableFuture<TODO>  queryChanges(@NotNull final ChangeQuery change) {
        new Thread(() -> {
            synchronized (this.statementLock) {
                try {
                    //do stuff soon
                }
            }
        });
    }

    @Override
    public void updateChanges(@NotNull final ChangeUpdate change) {
        new Thread(() -> {
            synchronized (this.statementLock) {
                try {
                    //do stuff soon
                }
            }
        });
    }

    public boolean startStorage() {
        final String connectionString = String.format("jdbc:sqlite:%s", this.file.getPath());
        try {
            this.connection = DriverManager.getConnection(connectionString);
        } catch (final Exception e) {
            LOGGER.error("Failed to initialize SQLite connection", e);
        }
        try {
            final Statement statement = this.getConnection().createStatement();
             statement.addBatch(DDL1);
             statement.addBatch(DDL2);
             statement.executeBatch();
        } catch (final Exception e) {
            LOGGER.error("Failed to create event table", e);
        }
        return this.connection != null;
    }

    public void stopStorage() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (final Throwable throwable) {
                LOGGER.error("Failed to close SQLite connection", throwable);
            }
        }
    }


    private Connection getConnection() {
        return this.connection;
    }
}
