package io.ilyahaker.sokoban;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class Sokoban extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("sokoban").setExecutor((sender, command, label, args) -> {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command is for players only!");
                return false;
            }

            Player player = (Player) sender;
            Inventory inventory = Bukkit.createInventory(player, 9 * 6, "Sokoban");
            player.openInventory(inventory);
            return true;
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
