package net.cubeslide.oneblock.oneblockcore.auctionhouse;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.inventory.ItemStack;

public class AuctionItem {

    @Getter
    @Setter
    private UUID owner;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String itemID;
    @Getter
    @Setter
    private ItemStack item;
    @Getter
    @Setter
    private int price;
    @Getter
    @Setter
    private long expires;

    AuctionItem(UUID owner, String name, ItemStack is, int price, long expires){
        this.owner = owner;
        this.name = name;
        this.item = is;
        this.price = price;
        this.expires = expires;
        this.itemID = AuctionManager.getRandomKey();
    }
    public boolean isExpired() {
        return System.currentTimeMillis() > this.expires;
    }
}
