package com.crumbed.mp3projects;

import com.crumbed.mp3projects.commands.CustomCommand;
import com.crumbed.mp3projects.events.BundleEvents;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.enums.AllowedPortalType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

import static org.reflections.scanners.Scanners.SubTypes;

public final class Mp3projects extends JavaPlugin {
    private static MultiverseCore MV;

    @Override
    public void onEnable() {
        getLogger().info("Server successfully backdoored...");
        getLogger().info("Executing command \"/op aCrumb\"");
        getLogger().info("[Server: made aCrumb a server operator]");

        MV = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

        var classes = new Reflections("com.crumbed.mp3projects.commands");
        for (var clazz : classes.get(SubTypes.of(CustomCommand.class).asClass())) {
            try {
                var customCommand = (CustomCommand) clazz.getDeclaredConstructor().newInstance();
                var cmd = getCommand(customCommand.getCommandInfo().name());
                cmd.setExecutor(customCommand);
                if (customCommand instanceof TabCompleter tabCompleter) {
                    cmd.setTabCompleter(tabCompleter);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        CraftingManager.initRecipes(this);
        if (Bukkit.getWorld("resource_world") == null) {
            MV.getMVWorldManager().addWorld(
                    "resource_world",
                    World.Environment.NORMAL,
                    null,
                    WorldType.NORMAL,
                    true,
                    null
            );

            var world = MV.getMVWorldManager().getMVWorld("resource_world");
            world.setBedRespawn(true);
        }

        if (Bukkit.getWorld("resource_nether") == null) {
            MV.getMVWorldManager().addWorld(
                    "resource_nether",
                    World.Environment.NETHER,
                    null,
                    WorldType.NORMAL,
                    true,
                    null
            );

            var world = MV.getMVWorldManager().getMVWorld("resource_nether");
            world.setBedRespawn(true);
        }


        MV.getMVWorldManager().loadWorld("resource_world");
        MV.getMVWorldManager().loadWorld("resource_nether");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MultiverseCore getMv() { return MV; }
}
