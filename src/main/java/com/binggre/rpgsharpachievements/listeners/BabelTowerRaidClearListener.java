package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.BabelTowerRaidClearAchievement;
import com.binggre.rpgsharpcollectionbook.apis.CollectionAddEvent;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.dev.babeltower.event.BabelTowerRaidIsOverEvent;

public class BabelTowerRaidClearListener implements Listener {

    @EventHandler
    public void onBabelTowerRaidClear(BabelTowerRaidIsOverEvent event) {

        if (!event.getRaidResult().isSucceeded()) {
            return;
        }
        String nickname = event.getRaidResult().getRaid().getPlayerTower().getNickname();
        Player player = Objects.requireNonNull(Bukkit.getPlayer(nickname));
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();

        if (!(now instanceof BabelTowerRaidClearAchievement achievement)) {
            return;
        }
        if (!achievement.complete(playerAchievement, event.getRaidResult())) {
            return;
        }
        playerAchievement.sendComplete();
    }
}
