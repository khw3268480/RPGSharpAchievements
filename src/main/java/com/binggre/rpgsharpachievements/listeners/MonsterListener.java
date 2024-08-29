package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.MonsterHuntAchievement;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MonsterListener implements Listener {

    @EventHandler
    public void onMonsterDeath(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        Player player = victim.getKiller();
        if (player == null) {
            return;
        }
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();
        if (!(now instanceof MonsterHuntAchievement achievement)) {
            return;
        }
        if (achievement.complete(playerAchievement, victim)) {
            playerAchievement.sendComplete();
        }
    }
}