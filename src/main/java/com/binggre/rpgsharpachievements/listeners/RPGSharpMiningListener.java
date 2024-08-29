package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.MiningAchievement;
import com.binggre.rpgsharpmining.event.MiningSuccessEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RPGSharpMiningListener implements Listener {

    public RPGSharpMiningListener() {
    }

    @EventHandler
    public void onMining(MiningSuccessEvent event) {
        Player player = event.getBlockBreakEvent().getPlayer();

        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();

        if (!(now instanceof MiningAchievement achievement)) {
            return;
        }

        if (!achievement.complete(playerAchievement)) {
            return;
        }
        playerAchievement.sendComplete();
    }
}
