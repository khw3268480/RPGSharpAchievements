package com.binggre.rpgsharpachievements.objects.itemgroups;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemGroupGUI implements InventoryHolder {

    private final ItemGroup itemGroup;
    private final Inventory inventory;

    public ItemGroupGUI(String name) {
        this.itemGroup = ItemGroup.getGroups().get(name);
        this.inventory = Bukkit.createInventory(this, (6 * 9), name);
        this.refresh();
    }

    public void saveItemGroup() {
        this.itemGroup.setItemStacks(this.inventory);
        this.itemGroup.serialize();
        this.itemGroup.write();
    }

    private void refresh() {
        List<ItemStack> itemStacks = itemGroup.getItemStacks();
        int size = itemStacks.size();

        for (int i = 0; i < size; i++) {
            ItemStack itemStack = itemStacks.get(i);
            this.inventory.setItem(i, itemStack);
        }
    }

    @Override
    @Nonnull
    public Inventory getInventory() {
        return inventory;
    }

}
