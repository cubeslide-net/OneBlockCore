package net.cubeslide.oneblock.oneblockcore.commands;

import net.cubeslide.oneblock.oneblockcore.OneBlockCore;
import net.cubeslide.oneblock.oneblockcore.utils.MessageHandler;
import net.cubeslide.oneblock.oneblockcore.utils.SuccessfulTpaEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaCommand extends MessageHandler implements CommandExecutor{


    static HashMap<UUID, UUID> targetMap = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final OneBlockCore instance = OneBlockCore.getInstance();
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageHandler.getPrefix() + getNoPlayer());
            return true;
        }
        if (command.getName().equals("tpa")) {
            if (args.length == 1) {
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                    sender.sendMessage(MessageHandler.getPrefix() + getPlayerNotFound());
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                final Player senderP = (Player) sender;
                if (target.getUniqueId().equals(senderP.getUniqueId())) {
                    sender.sendMessage(MessageHandler.getPrefix() + "§cYou may not teleport to yourself.");
                    return true;
                }
                if (targetMap.containsKey(senderP.getUniqueId())) {
                    sender.sendMessage(MessageHandler.getPrefix() + "§cYou already have a pending request.");
                    return false;
                }
                target.sendMessage(MessageHandler.getPrefix() + senderP.getName() + " wants to teleport to you. \n" + MessageHandler.getPrefix() + "Type " + "§3/tpaccept§7" + " to accept this request.\n" + MessageHandler.getPrefix() + "Type " + "§3/tpdeny§7" + " to deny this request.\n" + MessageHandler.getPrefix() + "§eYou have 5 minutes to respond.");
                targetMap.put(senderP.getUniqueId(), target.getUniqueId());
                sender.sendMessage(MessageHandler.getPrefix() + "Send TPA request to " + target.getName() + ".");
                (new BukkitRunnable() {
                    public void run() {
                        targetMap.remove(senderP.getUniqueId());
                    }
                }).runTaskLaterAsynchronously(instance, 6000L);
            } else {
                sender.sendMessage(MessageHandler.getPrefix() + getSyntaxPrefix() + "tpa (Player)");
            }
            return true;
        }
        if (command.getName().equals("tpaccept") || command.getName().equals("tpyes")) {
            final Player senderP = (Player) sender;
            if (targetMap.containsValue(senderP.getUniqueId())) {
                sender.sendMessage(MessageHandler.getPrefix() + "TPA request accepted!");
                for (Map.Entry<UUID, UUID> entry : targetMap.entrySet()) {
                    if (entry.getValue().equals(senderP.getUniqueId())) {
                        Player tpRequester = Bukkit.getPlayer(entry.getKey());
                        SuccessfulTpaEvent event = new SuccessfulTpaEvent(tpRequester, tpRequester.getLocation());
                        Bukkit.getPluginManager().callEvent(event);
                        tpRequester.teleport(senderP);
                        targetMap.remove(entry.getKey());
                        break;
                    }
                }
            } else {
                sender.sendMessage(MessageHandler.getPrefix() + "§cYou don't have any pending requests!");
            }
            return true;
        }
        if (command.getName().equals("tpdeny") || command.getName().equals("tpno")) {
            final Player senderP = (Player) sender;
            if (targetMap.containsValue(senderP.getUniqueId())) {
                for (Map.Entry<UUID, UUID> entry : targetMap.entrySet()) {
                    if (entry.getValue().equals(senderP.getUniqueId())) {
                        targetMap.remove(entry.getKey());
                        Player originalSender = Bukkit.getPlayer(entry.getKey());
                        originalSender.sendMessage(MessageHandler.getPrefix() + "§cYour TPA request was denied!");
                        sender.sendMessage(MessageHandler.getPrefix() + "§cDenied TPA request.");
                        break;
                    }
                }
            } else {
                sender.sendMessage(MessageHandler.getPrefix() + "§cYou don't have any pending requests!");
            }
            return true;
        }
        return false;
    }
}

