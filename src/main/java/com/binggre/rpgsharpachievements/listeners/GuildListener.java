package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.GuildJoinAchievement;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.guild.GuildCreateEvent;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.guild.GuildJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class GuildListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(GuildCreateEvent event) {
        if (!event.isCancelled()) {
            this.processPlayerAchievement(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(GuildJoinEvent event) {
        if (!event.isCancelled()) {
            this.processPlayerAchievement(event.getPlayer());
        }
    }

    private void processPlayerAchievement(Player player) {
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();

        if (!(now instanceof GuildJoinAchievement achievement)) {
            return;
        }

        if (achievement.complete(playerAchievement)) {
            playerAchievement.sendComplete();
        }
    }
}