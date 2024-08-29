package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.itemgroups.ItemGroupGUI;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroupGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof ItemGroupGUI itemGroupGUI) {
            itemGroupGUI.saveItemGroup();
        } else if (holder instanceof RewardGroupGUI rewardGroupGUI) {
            rewardGroupGUI.saveRewardGroup();
        }
    }
}