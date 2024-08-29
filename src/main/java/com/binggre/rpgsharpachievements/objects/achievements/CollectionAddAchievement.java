package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;

import java.util.List;

public class CollectionAddAchievement extends Achievement{
    private final List<String> collectionNames;


    public CollectionAddAchievement(int level, String typeName, String objectName, int amount,
        String rewardGroup, String rewardCommand, List<String> collectionNames) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
        this.collectionNames = collectionNames;
    }

    public List<String> getCollectionNames() {
        return collectionNames;
    }

    public boolean complete(PlayerAchievement playerAchievement, String collectionName) {
        if (!collectionNames.contains(collectionName)){
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
