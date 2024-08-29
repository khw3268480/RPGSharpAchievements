package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.versus.objcets.Tier;

public class VersusPromoteAchievement extends Achievement {

    public VersusPromoteAchievement(int level, String typeName, String objectName, int amount, String rewardGroup, String rewardCommand) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
    }

    public boolean complete(PlayerAchievement playerAchievement, Tier toTier) {
        if (!toTier.getName().equals(this.objectName)) {
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