package net.cubeslide.oneblock.oneblockcore.utils;

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
}
