package com.binggre.rpgsharpachievements.objects.achievements;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.hj.rpgsharpdungeon.objects.Dungeon;

public class RaidAchievement extends Achievement {

    private final int partySize;
    private final int partyMaxSize;

    public RaidAchievement(int level, String typeName, String objectName, int amount, String rewardGroup, String rewardCommand, int partySize, int partyMaxSize) {
        super(level, typeName, objectName, amount, rewardGroup, rewardCommand);
        this.partySize = partySize;
        this.partyMaxSize = partyMaxSize;
    }

    public int getPartySize() {
        return partySize;
    }

    public int getPartyMaxSize() {
        return partyMaxSize;
    }

    public boolean complete(PlayerAchievement playerAchievement, int playerPartySize, Dungeon dungeon) {
        if (!this.objectName.equals(dungeon.getName())) {
            return false;
        }
        if (playerPartySize < partySize || playerPartySize > partyMaxSize) {
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