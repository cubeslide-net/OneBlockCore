package net.cubeslide.oneblock.oneblockcore.auctionhouse;

import net.cubeslide.oneblock.oneblockcore.utils.ItemBuilder;
import net.cubeslide.oneblock.oneblockcore.utils.Pagifier;
import net.cubeslide.oneblock.oneblockcore.utils.Util;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuctionListener implements Listener {

    private Map<Player, String> cacheDisplayName = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(e.getCurrentItem() == null) return;
        if (e.getInventory() == null) return;

        if(e.getView().getTitle().equalsIgnoreCase("§9Auctionhouse")) {
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.CHEST_MINECART)) {
                player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
                player.openInventory(AuctionManager.getPlayerAuctionMenu(player));
                return;
            } else if (e.getSlot() == 7) {
                if ((getNumber(Objects.requireNonNull(e.getInventory().getItem(13))) - 1) <= 0) return;
                player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
                e.getInventory().setItem(13, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, 0).name("§7Site: " + (getNumber(Objects.requireNonNull(e.getInventory().getItem(13))) - 1)).build());
                Pagifier<AuctionItem> items = AuctionManager.getAuctionItems();
                for (int i = 18; i < 54; i++) {
                    e.getInventory().setItem(i, new ItemStack(Material.AIR));
                }
                int id = 18;
                for (AuctionItem ai : items.getPage((getNumber(Objects.requireNonNull(e.getInventory().getItem(13))) - 1))) {
                    if (ai.getItem() == null) continue;
                    String time = AuctionManager.getTime(ai.getExpires());
                    ItemStack item = new ItemBuilder(ai.getItem().clone()).lore(Arrays.asList("§7Price§8: §6" + Util.coinsAsString(ai.getPrice()) + " §7Coins", "§7Expires in§8: §c" + time, "§0Id: " + ai.getItemID(), "§7Offer from §e" + ai.getName())).build();
                    e.getInventory().setItem(id, item);
                    id++;
                }
                return;
            }
        }
    }

    private int getNumber(ItemStack itemStack) {
        return Integer.parseInt(itemStack.getItemMeta().getDisplayName().split(" ")[1]);
    }
}
