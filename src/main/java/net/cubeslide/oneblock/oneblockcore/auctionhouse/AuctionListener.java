package net.cubeslide.oneblock.oneblockcore.auctionhouse;

import ch.luca.cubeslide.coinsapi.CoinsAPI;
import net.cubeslide.oneblock.oneblockcore.OneBlockCore;
import net.cubeslide.oneblock.oneblockcore.utils.ItemBuilder;
import net.cubeslide.oneblock.oneblockcore.utils.MessageHandler;
import net.cubeslide.oneblock.oneblockcore.utils.Pagifier;
import net.cubeslide.oneblock.oneblockcore.utils.Util;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AuctionListener implements Listener {

    private final Map<Player, String> cacheDisplayName = new HashMap<>();

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
                    ItemStack item = new ItemBuilder(ai.getItem().clone()).lore(Arrays.asList("§7Price§8: §6" + ai.getPrice() + " §7Coins", "§7Expires in§8: §c" + time, "§0Id: " + ai.getItemID(), "§7Offer from §e" + ai.getName())).build();
                    e.getInventory().setItem(id, item);
                    id++;
                }
                return;
            } else if (e.getSlot() == 8) {
                Pagifier<AuctionItem> items = AuctionManager.getAuctionItems();
                if ((items.getPages().size() - 1) < getNumber(Objects.requireNonNull(e.getInventory().getItem(13)))) return;
                player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
                e.getInventory().setItem(13, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, 0).name("§7Site: " + (getNumber(Objects.requireNonNull(e.getInventory().getItem(13))) + 1)).build());

                for (int i = 18; i < 54; i++) {
                    e.getInventory().setItem(i, new ItemStack(Material.AIR));
                }

                int id = 18;
                for (AuctionItem auctionItem : items.getPage((getNumber(Objects.requireNonNull(e.getInventory().getItem(13))) - 1))) {
                    if (auctionItem.getItem() == null) continue;
                    String time = AuctionManager.getTime(auctionItem.getExpires());
                    ItemStack item = new ItemBuilder(auctionItem.getItem().clone()).lore(Arrays.asList("§7Price§8: §6" + auctionItem.getPrice() + " §7Coins", "§7Expires in§8: §c" + time, "§0Id: " + auctionItem.getItemID(), "§7Offer from §e" + auctionItem.getName())).build();
                    e.getInventory().setItem(id, item);
                    id++;
                }
                return;
            } else {
                if (!e.getCurrentItem().hasItemMeta()) return;
                if (!Objects.requireNonNull(e.getCurrentItem().getItemMeta()).hasLore()) return;
                for (String lore : Objects.requireNonNull(e.getCurrentItem().getItemMeta().getLore())) {
                    if (lore.startsWith("§0Id: ")) {
                        String id = lore.split(" ")[1];
                        AuctionItem item = AuctionManager.getItemFromId(id, false);
                        if (item == null) {
                            player.sendMessage(MessageHandler.getPrefix() + "§cThis item is sold or is expired.");
                            return;
                        }
                        if (item.isExpired()) {
                            player.sendMessage(MessageHandler.getPrefix() + "§cThis item is expired.");
                            return;
                        }
                        if (item.getOwner().equals(player.getUniqueId())) return;
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
                        Inventory inv = Bukkit.createInventory(null, 5 * 9, "§aBuy item?");

                        ItemStack item0 = new ItemBuilder(Material.GREEN_TERRACOTTA, 1, 0).name("§aBuy (" + item.getPrice() + " Coins)").build();
                        ItemStack item2 = new ItemBuilder(Material.RED_TERRACOTTA, 1, 0).name("§cCancel").build();

                        inv.setItem(4, e.getCurrentItem());

                        inv.setItem(10, item0);
                        inv.setItem(11, item0);
                        inv.setItem(12, item0);
                        inv.setItem(19, item0);
                        inv.setItem(20, item0);
                        inv.setItem(21, item0);
                        inv.setItem(28, item0);
                        inv.setItem(29, item0);
                        inv.setItem(30, item0);

                        inv.setItem(14, item2);
                        inv.setItem(15, item2);
                        inv.setItem(16, item2);
                        inv.setItem(23, item2);
                        inv.setItem(24, item2);
                        inv.setItem(25, item2);
                        inv.setItem(32, item2);
                        inv.setItem(33, item2);
                        inv.setItem(34, item2);
                        player.openInventory(inv);
                        return;
                    }
                }
            }
        } else if (e.getView().getTitle().equalsIgnoreCase("§cMy offers")) {
            e.setCancelled(true);
            if (!e.getClickedInventory().equals(player.getInventory())) {
                if (e.getCurrentItem() == null) return;
                if (e.getCurrentItem().getType().equals(Material.EMERALD)) {
                    player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
                    player.openInventory(AuctionManager.getAuctionMenu());
                    return;
                } else {
                    if (!e.getCurrentItem().hasItemMeta()) return;
                    if (!e.getCurrentItem().getItemMeta().hasLore()) return;
                    for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
                        if (lore.startsWith("§0Id: ")) {
                            String id = lore.split(" ")[1];
                            AuctionItem item = AuctionManager.getItemFromId(id, true);
                            if (item == null) continue;
                            if (item.isExpired()) {
                                if (e.isLeftClick()) {
                                    Calendar c = Calendar.getInstance();
                                    c.setTimeZone(TimeZone.getTimeZone("CET"));
                                    int jahr = c.get(Calendar.YEAR);
                                    int monat = c.get(Calendar.MONTH);
                                    int tag = c.get(Calendar.DAY_OF_MONTH);
                                    int stunde = c.get(Calendar.HOUR_OF_DAY);
                                    int minute = c.get(Calendar.MINUTE);
                                    int sekunde = c.get(Calendar.SECOND);
                                    int tage = tag + 7;
                                    c.set(jahr, monat, tage, stunde, minute, sekunde);

                                    long expires = c.getTimeInMillis();

                                    item.setExpires(expires);
                                    if (AuctionManager.expiredAuctionItems.contains(item)) {
                                        AuctionManager.expiredAuctionItems.remove(item);
                                    }
                                    AuctionManager.auctionItems.add(item);
                                    player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(OneBlockCore.getInstance(), () -> {
                                        player.openInventory(AuctionManager.getPlayerAuctionMenu(player));
                                    }, 3L);
                                    return;
                                }
                                if (e.isRightClick()) {
                                    if (Util.haveStorage(player) == 0) {
                                        e.getView().close();
                                        player.sendMessage(MessageHandler.getPrefix() + "§cYou didn't have enough space in your inventory.");
                                        return;
                                    }
                                    AuctionManager.removeAuctionItem(player, item);
                                    player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
                                    player.openInventory(AuctionManager.getPlayerAuctionMenu(player));
                                    return;
                                }
                            }
                            if (e.isRightClick()) {
                                if (Util.haveStorage(player) == 0) {
                                    e.getView().close();
                                    player.sendMessage(MessageHandler.getPrefix() + "§cYou didn't have enough space in your inventory.");
                                    return;
                                }
                                AuctionManager.removeAuctionItem(player, item);
                                player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1F, 1F);
                                player.openInventory(AuctionManager.getPlayerAuctionMenu(player));
                                return;
                            }
                            return;
                        }
                    }
                }
            } else {
                if (e.getClickedInventory().equals(player.getInventory()) && e.getCurrentItem() != null) {
                    if (e.getCurrentItem().getType().equals(Material.AIR)) return;
                    List<AuctionItem> items = AuctionManager.getAuctionItemsForUUID(player.getUniqueId());
                    if (items.size() >= 10) {
                        e.getView().close();
                        player.sendMessage(MessageHandler.getPrefix() + "§cYou reached the maximum amount.");
                        return;
                    }
                    if (e.getCurrentItem() == null) return;

                    if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                        cacheDisplayName.put(player, e.getCurrentItem().getItemMeta().getDisplayName());
                    }
                    new AnvilGUI.Builder()
                            .onComplete((player1, s) -> {

                                String am = s.replace("Coins", "").replace(" ", "");
                                if (!Util.isInt(am)) {
                                    player1.sendMessage(MessageHandler.getPrefix() + "§cYou must enter a number.");
                                    return AnvilGUI.Response.text(s);
                                }

                                int amount = Integer.parseInt(am);

                                if (amount < 5) {
                                    player1.sendMessage(MessageHandler.getPrefix() + "The minimum amount is §65 Coins§7.");
                                    return AnvilGUI.Response.text(s);
                                }
                                Calendar c = Calendar.getInstance();
                                c.setTimeZone(TimeZone.getTimeZone("CET"));
                                int jahr = c.get(Calendar.YEAR);
                                int monat = c.get(Calendar.MONTH);
                                int tag = c.get(Calendar.DAY_OF_MONTH);
                                int stunde = c.get(Calendar.HOUR_OF_DAY);
                                int minute = c.get(Calendar.MINUTE);
                                int sekunde = c.get(Calendar.SECOND);
                                int tage = tag + 7;
                                c.set(jahr, monat, tage, stunde, minute, sekunde);

                                long expires = c.getTimeInMillis();

                                String name = null;
                                if (cacheDisplayName.containsKey(player1)) {
                                    name = cacheDisplayName.get(player1);
                                    cacheDisplayName.remove(player1);
                                }

                                AuctionManager.addAuctionItem(player1, new ItemBuilder(e.getCurrentItem().clone()).name(name).build(), amount, expires);
                                player1.getInventory().setItem(e.getSlot(), null);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(OneBlockCore.getInstance(), () -> {
                                    player1.openInventory(AuctionManager.getPlayerAuctionMenu(player1));
                                }, 3L);
                                return AnvilGUI.Response.close();
                            }).preventClose().text("Preis").item(new ItemBuilder(e.getCurrentItem().clone()).name("0 Coins").build()).text("Price").plugin(OneBlockCore.getInstance()).open(player);
                    return;
                }
            }
        } else if (e.getView().getTitle().equalsIgnoreCase("§aBuy item?")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType().equals(Material.GREEN_TERRACOTTA)) {
                ItemStack itemStack = e.getInventory().getItem(4);
                if (!itemStack.hasItemMeta()) return;
                if (!itemStack.getItemMeta().hasLore()) return;
                for (String lore : itemStack.getItemMeta().getLore()) {
                    if (lore.startsWith("§0Id: ")) {
                        String id = lore.split(" ")[1];
                        AuctionItem item = AuctionManager.getItemFromId(id, false);
                        if (item == null) {
                            player.sendMessage(MessageHandler.getPrefix() + "§cThis item is sold or is expired.");
                            e.getView().close();
                            return;
                        }
                        if (!AuctionManager.auctionItems.contains(item)) {
                            player.sendMessage(MessageHandler.getPrefix() + "§cThis item is expired.");
                            e.getView().close();
                            return;
                        }
                        if (Util.haveStorage(player) == 0) {
                            player.sendMessage(MessageHandler.getPrefix() + "§cYou didn't have enough space in your inventory.");
                            e.getView().close();
                            return;
                        }
                        if (item.isExpired()) {
                            player.sendMessage(MessageHandler.getPrefix() + "§cThis item is expired.");
                            e.getView().close();
                            return;
                        }
                        long money = CoinsAPI.getInstance().getCoinsRepository().getCoins(player.getUniqueId());
                        long toPay = item.getPrice();
                        if (money < toPay) {
                            player.sendMessage(MessageHandler.getPlayerNotEnoughCoins());
                            e.getView().close();
                            return;
                        }
                        CoinsAPI.getInstance().getCoinsRepository().removeCoins(player.getUniqueId(), item.getPrice());
                        CoinsAPI.getInstance().getCoinsRepository().addCoins(item.getOwner(), item.getPrice());
                        //TODO: Update Score

                        Player owner = Bukkit.getPlayer(item.getOwner());
                        if (owner != null) {
                            //TODO: Update Score
                            owner.sendMessage(MessageHandler.getPrefix() + "Your item §e" + item.getItem().getType().name().toUpperCase() + " §7has been sold for §6" + item.getPrice() + " Coins §7from §a" + player.getDisplayName() + "§7.");
                            owner.sendMessage(MessageHandler.getPrefix() + "§a+ §6" + item.getPrice() + " Coins");
                        }
                        e.getView().close();
                        AuctionManager.removeAuctionItem(player, item);
                        player.sendMessage(MessageHandler.getPrefix() + "§aYou successfully bought the item.");
                        player.sendMessage(MessageHandler.getPrefix() + "§c- §6"  + item.getPrice() + " Coins");
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                        return;
                    }
                }
                return;
            } else if (e.getCurrentItem().getType().equals(Material.RED_TERRACOTTA)) {
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1F, 1F);
                player.openInventory(AuctionManager.getAuctionMenu());
                return;
            }
        }
    }

    private int getNumber(ItemStack itemStack) {
        return Integer.parseInt(itemStack.getItemMeta().getDisplayName().split(" ")[1]);
    }
}
