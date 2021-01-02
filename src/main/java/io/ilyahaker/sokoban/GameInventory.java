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

    private int currentColumn, currentRow;

    private final HashMap<GameObject, Pair<Integer, Integer>> boxes = new HashMap<>();

    private Pair<Integer, Integer> playerPosition;

    public GameInventory(String title, GameObject[][] matrix, Pair<Integer, Integer> playerPosition) {
        inventory = Bukkit.createInventory(this, 6 * 9, title);
        this.playerPosition = playerPosition;
        this.currentRow = matrix.length > 6 ? Math.max(playerPosition.getKey() - 3, 0) : 0;
        this.currentColumn = matrix[0].length > 9 ? Math.max(playerPosition.getValue() - 5, 0) : 0;
        this.matrix = matrix;

        fillInventory();
        inventory.setItem((playerPosition.getKey() - currentRow) * 9 + playerPosition.getValue() - currentColumn, new GamePlayerImpl().getItem());
    }

    private void fillInventory() {
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
    }

    public void inventoryClick(int row, int column) {
        int differenceRow = Math.abs(playerPosition.getKey() - row - currentRow),
                differenceColumn = Math.abs(playerPosition.getValue() - column - currentColumn);

        //inverse XOR operation
        if ((differenceRow == 1 && differenceColumn == 0) == (differenceColumn == 1 && differenceRow == 0)) {
            return;
        }

        Pair<Integer, Integer> currentPlayerPosition = new Pair<>(row + currentRow, column + currentColumn);
        boolean needFilling = false;
        if (matrix.length > 6 && matrix.length - currentPlayerPosition.getKey() >= 3) {
            currentRow = Math.max(currentPlayerPosition.getKey() - 3, 0);
            needFilling = true;
        }

        if (needFilling) {
            fillInventory();
        } else {
            if (playerPosition.getKey() < matrix.length && playerPosition.getValue() < matrix[0].length) {
                GameObject object = matrix[playerPosition.getKey()][playerPosition.getValue()];
                ItemStack itemStack = object == null ? null : object.getItem();
                inventory.setItem((playerPosition.getKey() - currentRow) * 9 + playerPosition.getValue() - currentColumn, itemStack);
            } else {
                inventory.setItem((playerPosition.getKey() - currentRow) * 9 + playerPosition.getValue() - currentColumn, null);
            }
        }

        playerPosition = currentPlayerPosition;
        inventory.setItem((playerPosition.getKey() - currentRow) * 9 + playerPosition.getValue() - currentColumn, new GamePlayerImpl().getItem());
    }
}