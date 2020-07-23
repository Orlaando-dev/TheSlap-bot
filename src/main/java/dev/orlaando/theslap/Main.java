package dev.orlaando.theslap;

import dev.orlaando.theslap.commands.Command;
import dev.orlaando.theslap.storage.SQLiteStorage;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static JDA client;

    private static SQLiteStorage sqLiteStorage;

    public static void main(String[] args) {
        try {
             JDA jda = JDABuilder.createDefault("token")
                    .setActivity(Activity.playing("use !help"))
                    .build();

            client = jda;
            LOGGER.info("Logged in as " + jda.getSelfUser().getAsTag());

            Command.registerCommands();
            LOGGER.info("Registed commands.");
        } catch (LoginException e) {
            LOGGER.error("Failed to login", e);
        }

        try {
            sqLiteStorage = new SQLiteStorage();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (sqLiteStorage == null || !sqLiteStorage.startStorage()) {
            LOGGER.error("Failed to start storage system");
        }
    }

    @NotNull public SQLiteStorage getSqLiteStorage() {
        return sqLiteStorage;
    }
}
