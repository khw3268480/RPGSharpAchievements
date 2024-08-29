package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import java.util.List;

public class ChangeJobAchievement extends Achievement {
    private final List<String> jobs;
    public ChangeJobAchievement(int level, String typeName, String objectName, int amount,
        String rewardGroup, String rewardCommand,List<String> jobs) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
        this.jobs = jobs;
    }

    public List<String> getJobs(){
        return this.jobs;
    }

    public boolean complete(PlayerAchievement playerAchievement, String job) {
        if(!jobs.contains(job)){
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
