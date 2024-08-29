package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.RaidAchievement;
import com.hj.rpgsharp.rpg.plugins.party.objects.Party;
import com.hj.rpgsharpdungeon.api.DungeonCompleteEvent;
import com.hj.rpgsharpdungeon.objects.Dungeon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DungeonListener implements Listener {

    @EventHandler
    public void onClear(DungeonCompleteEvent event) {

        Party party = event.getParty();
        int partySize = party.getMembers().size();
        Dungeon dungeon = event.getDungeon();

        PlayerAchievementLoader loader = PlayerAchievementLoader.getInstance();
        for (Player player : party.getPlayers()) {
            PlayerAchievement playerAchievement = loader.get(player);
            Achievement now = playerAchievement.getNow();
            if (!(now instanceof RaidAchievement achievement)) {
                continue;
            }
            if (achievement.complete(playerAchievement, partySize, dungeon)) {
                playerAchievement.sendComplete();
            }
        }
    }
}