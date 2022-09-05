package net.cubeslide.oneblock.oneblockcore.utils;

public class MessageHandler {



    private static String prefix = "§3CubeSlide §8» §7";
    private static String noPermission = "§cYou don't have permission to do that.";
    private static String noPlayer = "§cYou have to be a player to do that.";
    private static String playerNotFound = "§cThe target player could not be found.";
    private static String syntaxPrefix = "§cPlease use: §e/";
    private static String playerNotEnoughCoins = "§cYou don't have enough coins.";

 

    

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

    public static String getPlayerNotEnoughCoins() {
        return playerNotEnoughCoins;
    }
}
