package net.cubeslide.oneblock.oneblockcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static Location spawnLocation() {
        return new Location(Bukkit.getWorlds().get(0), -0.5, 49.5, -0.5, (float) -179.9, 0);
    }

}
