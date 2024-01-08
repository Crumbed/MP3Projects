package com.crumbed.mp3projects.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class ShulkerRecipe {
    public static void createRecipe(JavaPlugin server) {
        final var item = new ItemStack(Material.SHULKER_SHELL);
        final var key = new NamespacedKey(server, "shulker_shell");
        var r = new ShapedRecipe(key, item);

        r.shape("DDD", "DED", "DDD");
        r.setIngredient('D', Material.DIAMOND);
        r.setIngredient('E', Material.ENDER_PEARL);

        Bukkit.addRecipe(r);
    }
}
