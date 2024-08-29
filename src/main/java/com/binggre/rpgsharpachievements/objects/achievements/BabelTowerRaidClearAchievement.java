package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import org.dev.babeltower.dto.BabelTowerRaidResultDTO;

public class BabelTowerRaidClearAchievement extends Achievement {

    public BabelTowerRaidClearAchievement(int level, String typeName, String objectName, int amount,
        String rewardGroup, String rewardCommand) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
    }

    public boolean complete(PlayerAchievement playerAchievement, BabelTowerRaidResultDTO result) {
        if(!result.isSucceeded()){
            return false;
        }
        int floor = Integer.parseInt(objectName);
        if(!(result.getRaid().getTower().getFloor() == floor)){
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
