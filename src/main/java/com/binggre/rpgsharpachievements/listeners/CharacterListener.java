package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.character.CharacterCreateEvent;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.character.CharacterLoadEvent;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.character.CharacterQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CharacterListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreate(CharacterCreateEvent event) {
        if (event.isCancelled()) {
            return;
        }
        PlayerAchievement read = PlayerAchievementLoader.getInstance().read(event.getPlayer());
        read.register();
        read.write();
    }

    @EventHandler
    public void onLoad(CharacterLoadEvent event) {
        PlayerAchievement read = PlayerAchievementLoader.getInstance().read(event.getPlayer());
        read.register();
        read.write();
    }

    @EventHandler
    public void onQuit(CharacterQuitEvent event) {
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(event.getPlayer());
        playerAchievement.write();
        playerAchievement.unregister();
    }
}