package com.crumbed.mp3projects.heads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record PlayerHead(UUID ownerId, UUID killerId, Date dateKilled) implements Listener {
    public ItemStack getItem() {
        var lore = List.of(
                String.format("%sSlain by: %s%s", ChatColor.GREEN, ChatColor.RED, Bukkit.getPlayer(killerId).getDisplayName()),
                String.format("%sObtained on: %s%s", ChatColor.GREEN, ChatColor.RED, dateKilled.toString())
        );

        var head = new ItemStack(Material.PLAYER_HEAD);
        var meta = (SkullMeta) head.getItemMeta();
        meta.setOwnerProfile(Bukkit.getPlayer(ownerId).getPlayerProfile());
        meta.setDisplayName(ChatColor.GREEN + Bukkit.getPlayer(ownerId).getDisplayName() + "'s Head");
        meta.setLore(lore);
        head.setItemMeta(meta);
        return head;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        var item = e.getItemInHand();
        if (!item.getItemMeta().hasDisplayName() ||
                item.getType() != Material.PLAYER_HEAD ||
                !item.getItemMeta().getDisplayName().substring(0, 2).equals(ChatColor.GREEN+"")) return;

        var block = e.getBlockPlaced();
        var data = block.getBlockData();
    }
}
















