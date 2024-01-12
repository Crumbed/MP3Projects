package com.crumbed.mp3projects.commands;


import com.crumbed.mp3projects.Mp3projects;
import com.mojang.authlib.Environment;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@CommandInfo(name = "resource_world", requiresPlayer = true)
public class ResourceWorldCommand extends CustomCommand implements TabCompleter {

    @Override
    public void execute(CommandSender sender, String[] args) {
        final var p = (Player) sender;
        final var wm = Mp3projects.getMv().getMVWorldManager();

        var loc = (args.length == 1) ? switch (args[0]) {
            case "alt" -> wm.getMVWorld("resource_world").getSpawnLocation();
            case "alt_nether" -> wm.getMVWorld("resource_nether").getSpawnLocation();
            case default -> wm.getSpawnWorld().getSpawnLocation();
        } : switch (p.getWorld().getName()) {
            case "resource_world" -> wm.getSpawnWorld().getSpawnLocation();
            case default -> wm.getMVWorld("resource_world").getSpawnLocation();
        };

        Mp3projects.getMv().getSafeTTeleporter().safelyTeleport(p, p, loc, true);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
        return List.of("main", "alt", "alt_nether");
    }
}
