package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.AreaEnterAchievement;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.area.AreaEnterEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AreaEnterListener implements Listener {

    @EventHandler
    public void onEnterArea(AreaEnterEvent event) {
        Player player = event.getPlayer();
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();

        if (!(now instanceof AreaEnterAchievement achievement)) {
            return;
        }
        if (!achievement.complete(playerAchievement, event.getArea().getId())) {
            return;
        }
        playerAchievement.sendComplete();
    }
}
