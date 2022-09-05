package net.cubeslide.oneblock.oneblockcore.auctionhouse;

import net.cubeslide.oneblock.oneblockcore.utils.ItemBuilder;
import net.cubeslide.oneblock.oneblockcore.utils.ItemSerializer;
import net.cubeslide.oneblock.oneblockcore.utils.Pagifier;
import net.cubeslide.oneblock.oneblockcore.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AuctionManager {

    public static List<AuctionItem> auctionItems = new ArrayList<AuctionItem>();
    public static List<AuctionItem> expiredAuctionItems = new ArrayList<AuctionItem>();

    private static ItemStack offers = new ItemBuilder(Material.EMERALD).name("§eOffers").lore(Arrays.asList("§7Here you can find all offers", "§7from other players")).build();
    private static ItemStack myOffers = new ItemBuilder(Material.CHEST_MINECART).name("§eMy offers").lore(Arrays.asList("§7Here you find your auction items in the auctionhouse")).build();
    private static ItemStack offerHelp = new ItemBuilder(Material.REDSTONE).name("§cNo Items found!").lore(Arrays.asList("§7You didn't offer any items in the moment.", "§7Click on a item in your inventory,", "§7to sell it in the auctionhouse")).build();

    public static Inventory getPlayerAuctionMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§cMy offers");
        inv.setItem(0, new ItemBuilder(myOffers).addGlow().build());
        inv.setItem(1, ItemBuilder.CYAN_GLASS);
        inv.setItem(2, ItemBuilder.CYAN_GLASS);
        inv.setItem(3, ItemBuilder.CYAN_GLASS);
        inv.setItem(4, offers);
        inv.setItem(5, ItemBuilder.CYAN_GLASS);
        inv.setItem(6, ItemBuilder.CYAN_GLASS);
        inv.setItem(7, ItemBuilder.CYAN_GLASS);
        inv.setItem(8, ItemBuilder.CYAN_GLASS);
        inv.setItem(9, ItemBuilder.GRAY_GLASS);
        inv.setItem(10, ItemBuilder.GRAY_GLASS);
        inv.setItem(11, ItemBuilder.GRAY_GLASS);
        inv.setItem(12, ItemBuilder.GRAY_GLASS);
        inv.setItem(13, ItemBuilder.GRAY_GLASS);
        inv.setItem(14, ItemBuilder.GRAY_GLASS);
        inv.setItem(15, ItemBuilder.GRAY_GLASS);
        inv.setItem(16, ItemBuilder.GRAY_GLASS);
        inv.setItem(17, ItemBuilder.GRAY_GLASS);

        List<AuctionItem> items = getAuctionItemsForUUID(player.getUniqueId());

        if(items.size() == 0) {
            inv.setItem(22, offerHelp);
        }

        inv.setItem(29, ItemBuilder.BARRIER);
        inv.setItem(30, ItemBuilder.BARRIER);
        inv.setItem(31, ItemBuilder.BARRIER);
        inv.setItem(32, ItemBuilder.BARRIER);
        inv.setItem(33, ItemBuilder.BARRIER);
        inv.setItem(38, ItemBuilder.BARRIER);
        inv.setItem(39, ItemBuilder.BARRIER);
        inv.setItem(40, ItemBuilder.BARRIER);
        inv.setItem(41, ItemBuilder.BARRIER);
        inv.setItem(42, ItemBuilder.BARRIER);
        int id = 29;

        for (AuctionItem item : items) {
            if(id == 34) {
                id = 38;
            } else if(id == 43) {
                break;
            }
            String time = getTime(item.getExpires());
            if(time.equalsIgnoreCase("Expired")) {
                ItemStack itemStack = new ItemBuilder(item.getItem().clone()).lore(Arrays.asList("§7Price: §6" + item.getPrice() + " Coins", "§c§lExpired!", "§0Id: " + item.getItemID(), "§7Leftclick to §aoffer §7again.", "§7Rightclick to §cremove §7it.")).build();
                inv.setItem(id, itemStack);
                id++;
            } else {
                ItemStack itemStack = new ItemBuilder(item.getItem().clone()).lore(Arrays.asList("§7Price: §6" + item.getPrice() + " Coins", "§7Expires in: §c" + time, "§0Id: " + item.getItemID(), "§7Rightclick to §cremove §7it.")).build();
                inv.setItem(id, itemStack);
                id++;
            }
        }
        return inv;
    }

    public static Inventory getAuctionMenu() {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§9Auctionhouse");
        inv.setItem(0, myOffers);
        inv.setItem(1, ItemBuilder.CYAN_GLASS);
        inv.setItem(2, ItemBuilder.CYAN_GLASS);
        inv.setItem(3, ItemBuilder.CYAN_GLASS);
        inv.setItem(4, new ItemBuilder(offers).addGlow().build());
        inv.setItem(5, ItemBuilder.CYAN_GLASS);
        inv.setItem(6, ItemBuilder.CYAN_GLASS);
        inv.setItem(7, ItemBuilder.PREVIOUS_PAGE);
        inv.setItem(8, ItemBuilder.NEXT_PAGE);
        inv.setItem(9, ItemBuilder.GRAY_GLASS);
        inv.setItem(10, ItemBuilder.GRAY_GLASS);
        inv.setItem(11, ItemBuilder.GRAY_GLASS);
        inv.setItem(12, ItemBuilder.GRAY_GLASS);
        inv.setItem(13, new ItemBuilder(ItemBuilder.GRAY_GLASS.clone()).name("§7Site: 1").build());
        inv.setItem(14, ItemBuilder.GRAY_GLASS);
        inv.setItem(15, ItemBuilder.GRAY_GLASS);
        inv.setItem(16, ItemBuilder.GRAY_GLASS);
        inv.setItem(17, ItemBuilder.GRAY_GLASS);

        int i = 18;

        Pagifier<AuctionItem> items = getAuctionItems();

        for(AuctionItem marketItem : items.getPage(0)) {
            if(marketItem.getItem() == null) continue;
            String time = getTime(marketItem.getExpires());
            ItemStack item = new ItemBuilder(marketItem.getItem().clone()).lore(Arrays.asList("§7Price: §6" + marketItem.getPrice() + " Coins", "§7Expires in: §c" + time, "§0Id: " + marketItem.getItemID(), "§7Seller: §e" + marketItem.getName())).build();
            inv.setItem(i, item);
            i++;
        }
        return inv;
    }

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

    public static void intialize(boolean save){
        File file = new File("plugins/OneBlockCore", "auctionHouse.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()) {
            try {
                file.createNewFile();
                cfg.save(file);
                return;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        if(save){
            List<String> items = new ArrayList<String>();
            for(AuctionItem ai : auctionItems){
                String s = ai.getOwner().toString() + ";" + ai.getPrice() + ";" + ItemSerializer.serializeToString(ai.getItem()) + ";" + ai.getExpires() + ";" + ai.getName();
                items.add(s);
            }
            for(AuctionItem ai : expiredAuctionItems){
                String s = ai.getOwner().toString() + ";" + ai.getPrice() + ";" + ItemSerializer.serializeToString(ai.getItem()) + ";" + ai.getExpires() + ";" + ai.getName();
                items.add(s);
            }
            cfg.set("items", items);
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("CET"));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int hours = hour + 24;
        c.set(year, month, day, hour, minute, second);

        long newExpiresTime = c.getTimeInMillis();
        for(String s : cfg.getStringList("items")){
            String[] split = s.split(";");
            UUID owner = null;
            int price = -1;
            ItemStack is = null;
            long expires = -1;
            String name = null;
            try {
                if(split[3] != null && split[4] != null) {
                    owner = UUID.fromString(split[0]);
                    price = Integer.parseInt(split[1]);
                    is = ItemSerializer.deserializeFromString(split[2]);
                    expires = Long.parseLong(split[3]);
                    name = split[4];
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                owner = UUID.fromString(split[0]);
                price = Integer.parseInt(split[1]);
                is = ItemSerializer.deserializeFromString(split[2]);
                expires = newExpiresTime;
                name = "Unknown";
            }

            if(is == null){
                continue;
            }
            if(System.currentTimeMillis() > expires) {
                expiredAuctionItems.add(new AuctionItem(owner, name, is, price, expires));
                continue;
            }
            auctionItems.add(new AuctionItem(owner, name, is, price, expires));
        }
    }
}