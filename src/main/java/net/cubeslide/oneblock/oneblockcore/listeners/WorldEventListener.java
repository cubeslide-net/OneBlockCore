package net.cubeslide.oneblock.oneblockcore.listeners;

import net.cubeslide.oneblock.oneblockcore.OneBlockCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class WorldEventListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        if(player.hasPermission("OneBlockCore.spawnEdit")) return;

        if (!player.getWorld().getName().equalsIgnoreCase("world")) {
            OneBlockCore.getInstance().sendScoreboard(player);
        }

        if (player.hasPermission("OneBlockCore.spawnEdit")) return;

        if (player.getWorld().getName().equalsIgnoreCase("world")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();

        if (player.hasPermission("OneBlockCore.spawnEdit")) return;

        if (player.getWorld().getName().equalsIgnoreCase("world")) {
            event.setCancelled(true);
        }
    }

}
