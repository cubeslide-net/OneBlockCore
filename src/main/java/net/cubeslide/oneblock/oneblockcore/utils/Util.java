package net.cubeslide.oneblock.oneblockcore.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class Util {

    private long delay = System.currentTimeMillis();

    public boolean checkDelay() {
        if(System.currentTimeMillis() > this.delay+500) {
            this.delay = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public static String coinsAsString(int amount) {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(1).replace(",", "'");
    }

    public static int haveStorage(Player p) {
        int freeSpots = 0;
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) {
                freeSpots++;
            }
        }
        return freeSpots;
    }

    public static boolean isInt(String amount) {
        try {
            Integer.parseInt(amount);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
