package com.crumbed.mp3projects.events;

import com.crumbed.mp3projects.Mp3projects;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;

public class ResourceWorldEvents implements Listener {


    @EventHandler
    public void onPortalEnter(EntityPortalEnterEvent e) {
        final var world = e.getLocation().getWorld();

        Location loc = null;
        if (world.getName().equals("resource_world")) {
            loc = Mp3projects.getMv().getMVWorldManager().getMVWorld("resource_nether").getSpawnLocation();
        } else if (world.getName().equals("resource_nether")) {
            loc = Mp3projects.getMv().getMVWorldManager().getMVWorld("resource_world").getSpawnLocation();
        } else return;

        Mp3projects.getMv().getSafeTTeleporter().safelyTeleport(e.getEntity(), e.getEntity(), loc, true);
    }
}
