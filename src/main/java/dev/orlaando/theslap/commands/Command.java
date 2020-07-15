package dev.orlaando.theslap.commands;

import dev.orlaando.theslap.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class Command extends ListenerAdapter {

    private final String commandName;
    private final boolean supportOnly;

    public abstract void execute(MessageReceivedEvent event);

    public Command(String commandName, boolean supportOnly) {
        this.commandName = commandName;
        this.supportOnly = supportOnly;
        Main.client.addEventListener(this);
    }

    public static void registerCommands() {
        new CommandPing();
    }

    @Override public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContentDisplay().startsWith(commandName)) {
            if (supportOnly) {
                boolean hasSupportTeamRole = event.getMember().getRoles()
                        .stream().anyMatch(role ->
                        role.getName().equalsIgnoreCase("support team"));

                if (!hasSupportTeamRole) {
                    event.getChannel().sendMessage(":x: You must be a Support Team member in TheSlap Support server to do that.").queue();
                    return;
                }
            }
            execute(event);
        }
    }
}
