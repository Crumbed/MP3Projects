package com.crumbed.mp3projects.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static java.util.Objects.requireNonNull;

public abstract class CustomCommand implements CommandExecutor {
    private final CommandInfo commandInfo;

    public CustomCommand() {
        commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);
        requireNonNull(commandInfo, "Commands must have CommandInfo annotation");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!commandInfo.permission().isEmpty()
                && !sender.hasPermission(commandInfo.permission())) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command.");
            return true;
        }

        if (commandInfo.requiresPlayer()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players may execute this command.");
                return true;
            }
        }

        execute(sender, args);
        return true;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public CommandInfo getCommandInfo() { return commandInfo; }
}
