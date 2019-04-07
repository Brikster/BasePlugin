package ru.mrbrikster.baseplugin.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {

    @Setter private Inventory inventory;
    @Getter @Setter private Menu menu;

    public MenuHolder(Inventory inventory, Menu menu) {
        this.inventory = inventory;
        this.menu = menu;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}