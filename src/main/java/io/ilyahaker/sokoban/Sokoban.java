package io.ilyahaker.sokoban;

import io.ilyahaker.sokoban.object.GameObject;
import io.ilyahaker.sokoban.object.GameObjectImpl;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class Sokoban extends JavaPlugin {

    @Override
    public void onEnable() {
        GameObject[][] objects = new GameObject[10][15];
        objects[3][5] = new GameObjectImpl(new ItemStack(Material.WOODEN_AXE)) {
            @Override
            public ItemStack getItem() {
                return super.getItem();
            }
        };
        objects[9][5] = new GameObjectImpl(new ItemStack(Material.WOODEN_AXE)) {
            @Override
            public ItemStack getItem() {
                return super.getItem();
            }
        };
        objects[3][12] = new GameObjectImpl(new ItemStack(Material.WOODEN_AXE)) {
            @Override
            public ItemStack getItem() {
                return super.getItem();
            }
        };

        getCommand("sokoban").setExecutor((sender, command, label, args) -> {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command is for players only!");
                return false;
            }

            Player player = (Player) sender;
            GameInventory inventory = new GameInventory("Sokoban", objects, new Pair<>(1, 1));
            player.openInventory(inventory.getInventory());
            return true;
        });

        Bukkit.getPluginManager().registerEvents(new SokobanListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
