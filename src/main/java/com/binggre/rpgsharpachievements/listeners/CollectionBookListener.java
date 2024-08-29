package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.CollectionAddAchievement;
import com.binggre.rpgsharpcollectionbook.apis.CollectionAddEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CollectionBookListener implements Listener {

    @EventHandler

    public void onCollectionAdd(CollectionAddEvent event) {
        Player player = event.getPlayer();
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();

        if (!(now instanceof CollectionAddAchievement achievement)) {
            return;
        }
        String name = event.getCollectionBook().getName();
        if (!achievement.complete(playerAchievement, name)) {
            return;
        }
        playerAchievement.sendComplete();
    }
}
