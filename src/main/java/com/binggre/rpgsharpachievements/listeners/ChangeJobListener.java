package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.ChangeJobAchievement;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.RPGChangeJobEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChangeJobListener implements Listener {

    @EventHandler
    public void onChangeJob(RPGChangeJobEvent event) {
        Player player = event.getPlayer();

        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();

        if (!(now instanceof ChangeJobAchievement achievement)) {
            return;
        }
        if (!achievement.complete(playerAchievement, event.getTo())) {
            return;
        }
        playerAchievement.sendComplete();
    }

}
