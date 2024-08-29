package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.itemgroups.ItemGroup;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class ItemPickUpAchievement extends Achievement {

    public ItemPickUpAchievement(int level, String typeName, String objectName, int amount, String rewardGroup, String rewardCommand) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
    }

    public boolean complete(PlayerAchievement playerAchievement) {
        Player player = playerAchievement.toPlayer();
        ItemGroup itemGroup = ItemGroup.getGroups().get(this.objectName);
        if (itemGroup == null) {
            player.sendMessage("§c" + this.objectName + " 그룹이 존재하지 않습니다.");
            player.sendMessage("§c관리자에게 문의하세요.");
            return false;
        }
        if (!hasItems(player.getInventory(), itemGroup.getItemStacks())) {
            return false;
        }
        this.processComplete(playerAchievement);
        return true;
    }

    private boolean hasItems(Inventory inventory, List<ItemStack> itemStacks) {
        boolean equals;
        Map<ItemStack, Integer> items = Maps.newHashMap();
        for (ItemStack itemStack : itemStacks) {
            equals = false;
            for (ItemStack stack : items.keySet()) {
                if (stack.isSimilar(itemStack)) {
                    items.put(stack, itemStack.getAmount() + items.get(stack));
                    equals = true;
                    break;
                }
            }
            if (!equals) {
                items.put(itemStack, itemStack.getAmount());
            }
        }
        for (ItemStack itemStack : items.keySet()) {
            if (hasItem(inventory, itemStack, items.get(itemStack))) {
                return true;
            }
        }
        return false;
    }

    private boolean hasItem(Inventory inventory, ItemStack itemStack, int count) {
        int amount = 0;
        for (ItemStack storageContent : inventory.getStorageContents()) {
            if (storageContent != null && storageContent.isSimilar(itemStack)) {
                amount += storageContent.getAmount();
            }
        }
        return amount >= count;
    }
}