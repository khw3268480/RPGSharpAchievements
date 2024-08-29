package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.FieldBossClearAchievement;
import com.binggre.rpgsharpfieldboss.events.FieldBossDeathEvent;
import com.binggre.rpgsharpfieldboss.objects.BossAttacker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FieldBossListener implements Listener {

    @EventHandler
    public void onFieldBossDeath(EntityDamageByEntityEvent event) {
        @NotNull String entityName = event.getEntity().getName();
        Entity damager = event.getDamager();
        if (!(damager instanceof Player)) {
            return;
        }
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get((Player) damager);
        Achievement now = playerAchievement.getNow();
        if (!(now instanceof FieldBossClearAchievement achievement)) {
            return;
        }
        if (!(achievement.complete(playerAchievement, entityName))){
            return;
        }

        playerAchievement.sendComplete();
    }
}

