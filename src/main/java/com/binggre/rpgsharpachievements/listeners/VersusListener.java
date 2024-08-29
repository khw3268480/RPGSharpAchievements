package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.VersusPromoteAchievement;
import com.binggre.rpgsharpachievements.objects.achievements.VersusWinAchievement;
import com.binggre.versus.apis.VersusRankPromoteEvent;
import com.binggre.versus.apis.VersusStopEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VersusListener implements Listener {

    @EventHandler
    public void onStop(VersusStopEvent event) {
        if (event.isDraw()) {
            return;
        }
        Player player = event.getWinner().toPlayer();
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();
        if (!(now instanceof VersusWinAchievement achievement)) {
            return;
        }
        if (achievement.complete(playerAchievement)) {
            playerAchievement.sendComplete();
        }
    }

    @EventHandler
    public void onUpTier(VersusRankPromoteEvent event) {
        Player player = event.getPlayerVersus().toPlayer();
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();
        if (!(now instanceof VersusPromoteAchievement achievement)) {
            return;
        }
        if (achievement.complete(playerAchievement, event.getTo())) {
            playerAchievement.sendComplete();
        }
    }
}