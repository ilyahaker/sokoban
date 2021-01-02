package io.ilyahaker.sokoban;

import io.ilyahaker.sokoban.object.GameObject;
import io.ilyahaker.sokoban.object.GamePlayerImpl;
import javafx.util.Pair;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GameInventory implements InventoryHolder {

    @Getter
    private final Inventory inventory;

    private final GameObject[][] matrix;

    private int currentColumn = 0, currentRow = 0;

    private final HashMap<GameObject, Pair<Integer, Integer>> boxes = new HashMap<>();

    private Pair<Integer, Integer> playerPosition;

    public GameInventory(String title, GameObject[][] matrix, Pair<Integer, Integer> playerPosition) {
        inventory = Bukkit.createInventory(this, 6 * 9, title);
        this.playerPosition = playerPosition;

        this.matrix = matrix;
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 9; column++) {
                if (row + currentRow < matrix.length && column + currentColumn < matrix[0].length) {
                    ItemStack itemStack = matrix[row + currentRow][column + currentColumn] == null ? null : matrix[row + currentRow][column + currentColumn].getItem();
                    inventory.setItem(row * 9 + column, itemStack);
                } else {
                    inventory.setItem(row * 9 + column, null);
                }
            }
        }

        inventory.setItem(playerPosition.getKey() * 9 + playerPosition.getValue(), new GamePlayerImpl().getItem());
    }

    public void inventoryClick(int row, int column) {
        int differenceRow = Math.abs(playerPosition.getKey() - row),
                differenceColumn = Math.abs(playerPosition.getValue() - column);
        if ((differenceRow == 1 && differenceColumn == 0) == (differenceColumn == 1 && differenceRow == 0)) {
            return;
        }

        if (playerPosition.getKey() + currentRow < matrix.length && playerPosition.getValue() + currentColumn < matrix[0].length) {
            GameObject object = matrix[playerPosition.getKey() + currentRow][playerPosition.getValue() + currentColumn];
            ItemStack itemStack = object == null ? null : object.getItem();
            inventory.setItem(playerPosition.getKey() * 9 + playerPosition.getValue(), itemStack);
        } else {
            inventory.setItem(playerPosition.getKey() * 9 + playerPosition.getValue(), null);
        }
        playerPosition = new Pair<>(row, column);
        inventory.setItem(row * 9 + column, new GamePlayerImpl().getItem());
    }
}
