package io.ilyahaker.sokoban;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SokobanListener implements Listener {

    private Sokoban plugin;

    public SokobanListener(Sokoban plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onIntentoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) {
            return;
        }

        InventoryHolder inventoryHolder = clickedInventory.getHolder();
        if (!(inventoryHolder instanceof GameInventory)) {
            return;
        }

        event.setCancelled(true);
        if (event.getClick() != ClickType.LEFT) {
            return;
        }

        GameInventory inventory = (GameInventory) inventoryHolder;
        inventory.handleClick(event.getSlot() / 9, event.getSlot() % 9);
    }

}
