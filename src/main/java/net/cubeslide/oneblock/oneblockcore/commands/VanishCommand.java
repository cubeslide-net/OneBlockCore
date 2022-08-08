package net.cubeslide.oneblock.oneblockcore.commands;

import net.cubeslide.oneblock.oneblockcore.OneBlockCore;
import net.cubeslide.oneblock.oneblockcore.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VanishCommand extends MessageHandler implements CommandExecutor {

    public static ArrayList<Player> vanished = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            sender.sendMessage(MessageHandler.getPrefix() + getNoPlayer());
        Player player = (Player) sender;
        if (player.hasPermission("OneBlockCore.vanish") || player.isOp()) {
            if (args.length == 0) {
                if (vanished.contains(player)) {
                    vanished.remove(player);
                    player.sendMessage(MessageHandler.getPrefix() + "§7You left the §3Vanish Mode§7.");
                    for (Player all : Bukkit.getOnlinePlayers())
                        all.showPlayer(player);
                } else {
                    for (Player all : Bukkit.getOnlinePlayers())
                        all.hidePlayer(player);
                    vanished.add(player);
                    player.sendMessage(MessageHandler.getPrefix() + "§7You entered the §3Vanish Mode§7.");
                }
            }
        } else {
            player.sendMessage(MessageHandler.getPrefix() + getNoPermission());
        }
        return false;
    }
}
