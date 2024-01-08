package com.crumbed.mp3projects;

import com.crumbed.mp3projects.commands.CustomCommand;
import com.crumbed.mp3projects.crafting.ShulkerRecipe;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

import static org.reflections.scanners.Scanners.SubTypes;

public final class Mp3projects extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Server successfully backdoored...");
        getLogger().info("Executing command \"/op aCrumb\"");
        getLogger().info("[Server: made aCrumb a server operator]");


        var classes = new Reflections("com.crumbed.mp3projects.commands");
        for (Class<?> clazz : classes.get(SubTypes.of(CustomCommand.class).asClass())) {
            try {
                CustomCommand customCommand = (CustomCommand) clazz.getDeclaredConstructor().newInstance();
                getCommand(customCommand.getCommandInfo().name()).setExecutor(customCommand);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        ShulkerRecipe.createRecipe(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
