package net.cubeslide.oneblock.oneblockcore.auctionhouse;

import net.cubeslide.oneblock.oneblockcore.utils.ItemBuilder;
import net.cubeslide.oneblock.oneblockcore.utils.Pagifier;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AuctionManager {

    public static List<AuctionItem> auctionItems = new ArrayList<AuctionItem>();
    public static List<AuctionItem> expiredAuctionItems = new ArrayList<AuctionItem>();

    private static ItemStack offers = new ItemBuilder(Material.EMERALD).name("§eOffers").lore(Arrays.asList("§7Here you can find all offers", "§7from other players")).build();
    private static ItemStack myOffers = new ItemBuilder(Material.CHEST_MINECART).name("§eMy offers").lore(Arrays.asList("§7Here you find your auction items in the auctionhouse")).build();
    private static ItemStack offerHelp = new ItemBuilder(Material.REDSTONE).name("§cNo Items found!").lore(Arrays.asList("§7You didn't offer any items in the moment.", "§7Click on a item in your inventory,", "§7to sell it in the auctionhouse")).build();

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

    public static String getTime(long expires) {
        long d = expires / 1000 - System.currentTimeMillis() / 1000;
        int seconds = (int) d;
        String s = "Unknown";
        int hours = seconds / 3600;
        int minutes = seconds / 60;
        if(System.currentTimeMillis() > expires) {
            return "Expired";
        }
        if(hours != 0) {
            if(hours == 1) {
                s = hours + " hour";
            } else {
                s = hours + " hours";
            }
        } else if(minutes != 0) {
            if(minutes == 1) {
                s = minutes + " minute";
            } else {
                s = minutes + " minutes";
            }
        } else {
            if(seconds == 1) {
                s = seconds + " second";
            } else {
                s = seconds + " seconds";
            }
        }
        return s;
    }

    public static void addAuctionItem(Player player, ItemStack itemStack, int price, long expires) {
        auctionItems.add(new AuctionItem(player.getUniqueId(), player.getName(), itemStack, price, expires));
    }

    public static void removeAuctionItem(Player giveTo, AuctionItem item) {
        if (auctionItems.contains(item)) {
            auctionItems.remove(item);
            giveTo.getInventory().addItem(item.getItem());
        }
    }

    public static List<AuctionItem> getAuctionItemsForUUID(UUID uuid) {
        List<AuctionItem> items = new ArrayList<>();
        for (AuctionItem ai : auctionItems) {
            if (ai.getOwner().toString().equalsIgnoreCase(uuid.toString())) {
                items.add(ai);
            }
        }
        for (AuctionItem ai : expiredAuctionItems) {
            if (ai.getOwner().toString().equalsIgnoreCase(uuid.toString())) {
                items.add(ai);
            }
        }
        return items;
    }

    public static AuctionItem getItemFromId(String id, boolean expired) {
        for (AuctionItem ai : auctionItems) {
            if(ai.getItemID().equalsIgnoreCase(id)) {
                return ai;
            }
        }
        if(expired) {
            for(AuctionItem ai : expiredAuctionItems) {
                if(ai.getItemID().equalsIgnoreCase(id)) {
                    return ai;
                }
            }
        }
        return null;
    }

    public static Pagifier<AuctionItem> getAuctionItems() {
        Pagifier<AuctionItem> pages = new Pagifier<AuctionItem>(4*9);
        Iterator<AuctionItem> items = auctionItems.iterator();
        while (items.hasNext()) {
            AuctionItem item = items.next();
            if (item.isExpired()) {
                expiredAuctionItems.add(item);
                items.remove();
                continue;
            }
            pages.addItem(item);
        }
        return pages;
    }
}