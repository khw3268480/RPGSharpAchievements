package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;

public class FieldBossClearAchievement extends Achievement {

    public FieldBossClearAchievement(int level, String typeName, String objectName, int amount,
        String rewardGroup, String rewardCommand) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
    }

    public boolean complete(PlayerAchievement playerAchievement, String bossName) {
        if(!this.objectName.equals(bossName)){
            return false;
        }
        if (playerAchievement.addAmount() < this.amount) {
            playerAchievement.write();
            return false;
        }
        this.processComplete(playerAchievement);
        return true;
    }

    public boolean complete(PlayerAchievement playerAchievement) {
        this.processComplete(playerAchievement);
        return true;
    }
}
