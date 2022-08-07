package net.cubeslide.oneblock.oneblockcore.listeners;

import net.cubeslide.oneblock.oneblockcore.OneBlockCore;
import net.cubeslide.oneblock.oneblockcore.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        player.teleport(LocationUtils.spawnLocation());
        if(!player.hasPlayedBefore()) {
            Bukkit.broadcastMessage(OneBlockCore.getPREFIX() + "§aThe Player §2" + player.getName() + " is playing OneBlock for the first time! §7[§6#§e" + Bukkit.getOfflinePlayers().length + "§7]");
        }

        event.setJoinMessage("");
        OneBlockCore.getInstance().sendScoreboard(player);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if(player.getWorld().getName().equalsIgnoreCase("world")) {
            if(player.getLocation().getY() < 20) {
                player.teleport(LocationUtils.spawnLocation());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        OneBlockCore.getInstance().getBoards().remove(event.getPlayer().getUniqueId());
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if(player.getWorld().getName().equalsIgnoreCase("world")) {
            final Block block = event.getClickedBlock();
            if(block.getType().isInteractable() && block.getType() != Material.ENDER_CHEST) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();

            if(player.getWorld().getName().equalsIgnoreCase("world")) {
                    event.setCancelled(true);
            }
        }
    }



}
