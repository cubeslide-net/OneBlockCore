package net.cubeslide.oneblock.oneblockcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MessageHandler {



    private static String prefix = "§3CubeSlide §8» §7";
    private static String noPermission = "§cYou don't have permission to do that.";
    private static String noPlayer = "§cYou have to be a player to do that.";
    private static String playerNotFound = "§cThe target player could not be found.";
    private static String syntaxPrefix = "§cPlease use: §e/";

 

    

    public static String getPrefix() {
        return prefix;
    }

    public static String getNoPermission() {
        return noPermission;
    }

    public static String getNoPlayer() {
        return noPlayer;
    }

    public static String getPlayerNotFound() {
        return playerNotFound;
    }

    public static String getSyntaxPrefix() {
        return syntaxPrefix;
    }
}
