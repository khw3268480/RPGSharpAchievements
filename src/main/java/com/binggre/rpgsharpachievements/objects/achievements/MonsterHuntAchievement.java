package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import org.bukkit.entity.LivingEntity;

public class MonsterHuntAchievement extends Achievement {

    public MonsterHuntAchievement(int level, String typeName, String objectName, int amount, String rewardGroup, String rewardCommand) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
    }

    public boolean complete(PlayerAchievement playerAchievement, LivingEntity livingEntity) {
        String customName = livingEntity.getCustomName();
        if (customName == null) {
            return false;
        }
        if (!customName.equals(this.objectName.replace("&", "§"))) {
            return false;
        }
        if (playerAchievement.addAmount() < this.amount) {
            playerAchievement.write();
            return false;
        }
        this.processComplete(playerAchievement);
        return true;
    }
}