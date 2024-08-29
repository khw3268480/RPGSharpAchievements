package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;

public class LevelUpAchievement extends Achievement {

    private final int goalsLevel;

    public LevelUpAchievement(int level, String typeName, String objectName, int amount, String rewardGroup, String rewardCommand, int goalsLevel) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
        this.goalsLevel = goalsLevel;
    }

    public int getGoalsLevel() {
        return goalsLevel;
    }

    public boolean complete(PlayerAchievement playerAchievement, int level) {
        if (level >= this.goalsLevel) {
            this.processComplete(playerAchievement);
            return true;
        }
        return false;
    }

    public boolean complete(PlayerAchievement playerAchievement) {
        this.processComplete(playerAchievement);
        return true;
    }
}