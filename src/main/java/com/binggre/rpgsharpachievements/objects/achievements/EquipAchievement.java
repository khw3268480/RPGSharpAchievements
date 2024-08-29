package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import java.util.List;

public class EquipAchievement extends Achievement {

    private final List<String> datacode;

    public EquipAchievement(int level, String typeName, String objectName, int amount,
        String rewardGroup, String rewardCommand, List<String> datacodes) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
        this.datacode = datacodes;
    }

    public boolean complete(PlayerAchievement playerAchievement, String datacode) {
        if (!this.datacode.contains(datacode)) {
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
