package net.cubeslide.oneblock.oneblockcore.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SuccessfulTpaEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private Location locationBeforeTpa;
    private boolean isCancelled;

    public SuccessfulTpaEvent(Player player, Location locationBeforeTpa) {
        this.player = player;
        this.locationBeforeTpa = locationBeforeTpa;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getLocationBeforeTpa() {
        return locationBeforeTpa;
    }

    public void setLocationBeforeTpa(Location locationBeforeTpa) {
        this.locationBeforeTpa = locationBeforeTpa;
    }
}
