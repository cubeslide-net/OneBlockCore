package net.cubeslide.oneblock.oneblockcore.auctionhouse;

import java.util.Random;

public class AuctionManager {


    public static String getRandomKey() {
        String pattern = "";
        pattern += "abcdefghijklmnopqrstuvwxyz";
        pattern += pattern.toUpperCase();
        pattern += "0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        int length = random.nextInt(11) + 10;
        for (int i = 0; i < length; i++) {
            builder.append(pattern.charAt(random.nextInt(pattern.length())));
        }
        return builder.toString();
    }
}