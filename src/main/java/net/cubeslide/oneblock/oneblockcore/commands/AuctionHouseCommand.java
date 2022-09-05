package net.cubeslide.oneblock.oneblockcore.commands;

import net.cubeslide.oneblock.oneblockcore.OneBlockCore;
import net.cubeslide.oneblock.oneblockcore.auctionhouse.AuctionManager;
import net.cubeslide.oneblock.oneblockcore.utils.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuctionHouseCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(MessageHandler.getNoPlayer());
            return true;
        }
        Player player = (Player) sender;
        player.openInventory(AuctionManager.getAuctionMenu());
        return true;
    }
}
