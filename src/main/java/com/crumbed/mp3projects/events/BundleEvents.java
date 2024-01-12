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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import static com.crumbed.mp3projects.CraftingManager.BUNDLE;

public class BundleEvents implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem() == null || e.getItem().getType() != Material.BUNDLE) return;
        e.setCancelled(true);

        openInv(e.getPlayer(), e.getItem());
    }

    @EventHandler
    public void closeBundle(InventoryCloseEvent e) {
        if (e.getInventory().getSize() != 9) return;
        var p = (Player) e.getPlayer();
        final var mainHand = p.getInventory().getItemInMainHand();
        final var invName = e.getView().getTitle();

        Bukkit.getLogger().info(invName);
        if (!mainHand.hasItemMeta() && (
                (mainHand.getItemMeta().hasDisplayName()
                        ? mainHand.getItemMeta().getDisplayName()
                        : mainHand.getType().toString()
                )).equalsIgnoreCase(invName)
        ) return;

        ByteArrayInputStream menuData = null;
        try {
            var items = Arrays.stream(e.getInventory().getContents())
                    .map(x -> switch (x) {
                        case null -> null;
                        case ItemStack i -> CraftItemStack.asNMSCopy(i);
                    })
                    .toList()
                    .toArray(net.minecraft.world.item.ItemStack[]::new);

            Arrays.stream(items).filter(Objects::nonNull).forEach(x -> Bukkit.getLogger().info(x.toString()));
            menuData = NbtUtils.serializeItemsArray(items);
        } catch (IOException err) {
            err.printStackTrace();
        }

        var meta = mainHand.getItemMeta();
        meta.getPersistentDataContainer().set(BUNDLE, PersistentDataType.BYTE_ARRAY, menuData.readAllBytes());
        mainHand.setItemMeta(meta);
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
        var menu = Bukkit.createInventory(p, InventoryType.DISPENSER, (item.getItemMeta().hasDisplayName())
                ? item.getItemMeta().getDisplayName()
                : item.getType().toString().substring(0, 1) + item.getType().toString().substring(1).toLowerCase()
        );
        var data = item
                .getItemMeta()
                .getPersistentDataContainer()
                .get(BUNDLE, PersistentDataType.BYTE_ARRAY);
        try {
            var nmsItems = NbtUtils.deserializeItemsArray(new ByteArrayInputStream(data));
            var items = Arrays.stream(nmsItems)
                    .map(x -> switch (x) {
                        case null -> null;
                        case net.minecraft.world.item.ItemStack i -> CraftItemStack.asBukkitCopy(i);
                    })
                    .toList()
                    .toArray(ItemStack[]::new);
            menu.setContents(items);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }


        p.openInventory(menu);
    }















}
