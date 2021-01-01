package io.ilyahaker.sokoban.object;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GamePlayerImpl extends GameObjectImpl implements GamePlayer {
    public GamePlayerImpl() {
        super(new ItemStack(Material.PLAYER_HEAD));
    }
}
