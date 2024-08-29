package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;

public class AreaEnterAchievement extends Achievement {
    public AreaEnterAchievement(int level, String typeName, String objectName, int amount,
        String rewardGroup, String rewardCommand) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
    }

    public boolean complete(PlayerAchievement playerAchievement, String areaId) {
        if (!objectName.equals(areaId)){
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
