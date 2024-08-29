package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.LevelUpAchievement;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.RPGChangeExpEvent;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.RPGLevelUpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class LevelUpListener implements Listener {

    @EventHandler
    public void onLevelUp(RPGLevelUpEvent event) {
        processPlayerAchievement(event.getPlayer(), event.getTo());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExp(RPGChangeExpEvent event) {
        if (event.isCancelled()) {
            return;
        }
        processPlayerAchievement(event.getPlayer(), event.getRPGPlayer().getLevel());
    }

    private void processPlayerAchievement(Player player, int level) {
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();
        if (!(now instanceof LevelUpAchievement achievement)) {
            return;
        }
        if (achievement.complete(playerAchievement, level)) {
            playerAchievement.sendComplete();
        }
    }
}