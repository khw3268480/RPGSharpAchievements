package com.binggre.rpgsharpachievements.objects.reward;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class RewardGroupGUI implements InventoryHolder {

    private final RewardGroup rewardGroup;
    private final Inventory inventory;

    public RewardGroupGUI(String name) {
        this.rewardGroup = RewardGroup.getGroups().get(name);
        this.inventory = Bukkit.createInventory(this, (6 * 9), name + " 그룹 보상");
        this.refresh();
    }


    @Override
    @Nonnull
    public Inventory getInventory() {
        return inventory;
    }

    public void saveRewardGroup() {
        this.rewardGroup.setItemStacks(this.inventory);
        this.rewardGroup.serialize();
        this.rewardGroup.write();
    }

    private void refresh() {
        List<ItemStack> itemStacks = this.rewardGroup.getItemStacks();
        int size = itemStacks.size();

        for (int i = 0; i < size; i++) {
            ItemStack itemStack = itemStacks.get(i);
            this.inventory.setItem(i, itemStack);
        }
    }
}
