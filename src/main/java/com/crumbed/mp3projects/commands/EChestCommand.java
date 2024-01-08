package com.crumbed.mp3projects.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "echest", requiresPlayer = true)
public class EChestCommand extends CustomCommand {

    // /echest command opens an ender chest gui if you have one in your inventory
    @Override
    public void execute(CommandSender sender, String[] args) {
        var player = (Player) sender;

        if(!player.getInventory().contains(Material.ENDER_CHEST)) {
            player.sendMessage(ChatColor.RED + "You must have an ender chest in your inventory to use this command!");
            return;
        }

        player.openInventory(player.getEnderChest());
    }
}


























