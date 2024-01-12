package com.crumbed.mp3projects;

import com.crumbed.mp3projects.utils.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.InventoryMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class CraftingManager {
    public static NamespacedKey BUNDLE;



    public static void initRecipes(JavaPlugin server) {
        BUNDLE = new NamespacedKey(server, "data");

        shulkerShell(server);
        //bundle(server);
    }


    public static void bundle(JavaPlugin server) {
        final var item = new ItemStack(Material.BUNDLE);
        var meta = (BundleMeta) item.getItemMeta();
        meta.setItems(List.of(new ItemStack(Material.BARRIER, 64)));
        ByteArrayInputStream menuData = null;
        try {
            var items = new net.minecraft.world.item.ItemStack[9];
            menuData = NbtUtils.serializeItemsArray(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
        meta.getPersistentDataContainer().set(BUNDLE, PersistentDataType.BYTE_ARRAY, menuData.readAllBytes());
        item.setItemMeta(meta);

        final var key = new NamespacedKey(server, "bundle");
        var r = new ShapedRecipe(key, item);

        r.shape(
                "SLS",
                "LCL",
                "LLL"
        );
        r.setIngredient('S', Material.STRING);
        r.setIngredient('L', Material.LEATHER);
        r.setIngredient('C', Material.CHEST);

        Bukkit.addRecipe(r);
    }


    public static void shulkerShell(JavaPlugin server) {
        final var item = new ItemStack(Material.SHULKER_SHELL);
        final var key = new NamespacedKey(server, "shulker_shell");
        var r = new ShapedRecipe(key, item);

        r.shape("DDD", "DED", "DDD");
        r.setIngredient('D', Material.DIAMOND);
        r.setIngredient('E', Material.ENDER_PEARL);

        Bukkit.addRecipe(r);
    }
}
