package net.cubeslide.oneblock.oneblockcore.commands;

import net.cubeslide.oneblock.oneblockcore.utils.LocationUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        player.teleport(LocationUtils.spawnLocation());

        return true;
    }
}
