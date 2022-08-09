package net.cubeslide.oneblock.oneblockcore;

import fr.mrmicky.fastboard.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import net.cubeslide.oneblock.oneblockcore.commands.SpawnCommand;
import net.cubeslide.oneblock.oneblockcore.commands.TpaCommand;
import net.cubeslide.oneblock.oneblockcore.commands.VanishCommand;
import net.cubeslide.oneblock.oneblockcore.listeners.PlayerEventListener;
import net.cubeslide.oneblock.oneblockcore.listeners.WorldEventListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public final class OneBlockCore extends JavaPlugin {


    private static final String PREFIX = "§3OneBlock §8» §7";
    private static OneBlockCore instance;
    private static HashMap<UUID, FastBoard> boards;


    public static HashMap<UUID, FastBoard> getBoards() {
        return boards;
    }

    public static OneBlockCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        final PluginManager pluginManager = getServer().getPluginManager();
        instance = this;
        boards = new HashMap<>();

        pluginManager.registerEvents(new PlayerEventListener(), this);
        pluginManager.registerEvents(new WorldEventListener(), this);

        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpaccept").setExecutor(new TpaCommand());
        getCommand("tpdeny").setExecutor(new TpaCommand());
        getCommand("vanish").setExecutor(new VanishCommand());

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    sendScoreboard(player);
                }
            }
        }.runTaskTimerAsynchronously(getInstance(), 20, 20);
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendScoreboard(player);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void sendScoreboard(Player player) {
        FastBoard board;
        if (!boards.containsKey(player.getUniqueId())) {
            board = new FastBoard(player);
            boards.put(player.getUniqueId(), board);
            board.updateTitle("§8> §bOneBlock §8<");
        } else {
            board = boards.get(player.getUniqueId());
        }
        board.updateLines(Arrays.asList("§8| §5",
            "§8| §3OB Count",
            "§8| §7 " + PlaceholderAPI.setPlaceholders(player,
                "%aoneblock_visited_island_count%"),
            "§8| §7",
            "§8| §3Blocks to",
            "§8| §3next Phase",
            "§8| §7 " + PlaceholderAPI.setPlaceholders(player,
                "%aoneblock_visited_island_blocks_to_next_phase%"),
            "§8| §7",
            "§8| §3Next Phase",
            "§8| §7 " + PlaceholderAPI.setPlaceholders(player, "%aoneblock_visited_island_next_phase%"),
            "§8| §5",
            "§8| §3Online",
            "§8| §7 " + Bukkit.getOnlinePlayers().size()));
    }
}
