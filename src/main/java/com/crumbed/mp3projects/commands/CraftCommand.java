package com.crumbed.mp3projects.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;

@CommandInfo(name = "craft", requiresPlayer = true)
public class CraftCommand extends CustomCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        var player = (Player) sender;
        player.openWorkbench(player.getLocation(), true);
    }
}
