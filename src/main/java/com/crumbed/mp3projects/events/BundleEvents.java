package com.crumbed.mp3projects.events;

import com.crumbed.mp3projects.CraftingManager;
import com.crumbed.mp3projects.utils.NbtUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class BundleEvents implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem().getType() != Material.BUNDLE) return;
        e.setCancelled(true);

        openInv(e.getPlayer(), e.getItem());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClick() != ClickType.RIGHT) return;
        if (
                !(e.getCursor() != null && e.getCursor().getType() == Material.BUNDLE)
                && !(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.BUNDLE)
        ) return;
        e.setCancelled(true);
    }



    private void openInv(Player p, ItemStack item) {
        var menu = Bukkit.createInventory(p, InventoryType.DISPENSER, item.getItemMeta().getDisplayName());
        var data = item
                .getItemMeta()
                .getPersistentDataContainer()
                .get(CraftingManager.BUNDLE, PersistentDataType.BYTE_ARRAY);
        try {
            var nmsItems = NbtUtils.deserializeItemsArray(new ByteArrayInputStream(data));
            var items = Arrays.stream(nmsItems).map(CraftItemStack::asBukkitCopy).toList().toArray(ItemStack[]::new);
            menu.setContents(items);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }


        p.openInventory(menu);
    }















}
