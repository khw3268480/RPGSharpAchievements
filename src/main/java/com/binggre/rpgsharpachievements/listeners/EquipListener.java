package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.achievements.EquipAchievement;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.equipment.RPGItemEquipEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EquipListener implements Listener {

    @EventHandler
    public void onEquip(RPGItemEquipEvent event) {
        String dataCode = event.getRPGItem().getDataCode();
        Player player = event.getPlayer();

        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();

        if (!(now instanceof EquipAchievement achievement)) {
            return;
        }

        if (!achievement.complete(playerAchievement,dataCode)) {
            return;
        }
        playerAchievement.sendComplete();
    }
}
