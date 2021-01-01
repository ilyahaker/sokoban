package io.ilyahaker.sokoban.object;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public abstract class GameObjectImpl implements GameObject {

    @Getter
    private ItemStack item;

    public GameObjectImpl(ItemStack item) {
        this.item = item;
    }
}
