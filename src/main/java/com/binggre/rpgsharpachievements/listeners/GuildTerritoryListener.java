package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.GuildTerritoryJoinAchievement;
import com.binggre.rpgsharpterritory.apis.TerritoryJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GuildTerritoryListener implements Listener {

    @EventHandler
    public void onJoin(TerritoryJoinEvent event) {
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(event.getPlayer());
        Achievement now = playerAchievement.getNow();
        if (!(now instanceof GuildTerritoryJoinAchievement achievement)) {
            return;
        }
        if (achievement.complete(playerAchievement)) {
            playerAchievement.sendComplete();
        }
    }
}