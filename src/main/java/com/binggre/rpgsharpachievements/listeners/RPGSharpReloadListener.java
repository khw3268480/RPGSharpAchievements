package com.binggre.rpgsharpachievements.listeners;

import com.binggre.rpgsharpachievements.commands.AdminCommand;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.itemgroups.ItemGroup;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroup;
import com.hj.rpgsharp.rpg.apis.rpgsharp.RPGSharpAPI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.events.RPGSharpReloadEvent;
import com.hj.rpgsharp.rpg.apis.rpgsharp.socket.SocketClient;
import com.hj.rpgsharp.rpg.apis.rpgsharp.socket.SocketType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RPGSharpReloadListener implements Listener {

    @EventHandler
    public void onReload(RPGSharpReloadEvent event) {
        ItemGroup.registerAll();
        RewardGroup.registerAll();
        Achievement.registerAll();

        SocketClient socketClient = RPGSharpAPI.getSocketClient();
        socketClient.send(SocketType.CONSOLE_COMMAND, "길라잡이관리 " + AdminCommand.RELOAD_ITEM_GROUP);
        socketClient.send(SocketType.CONSOLE_COMMAND, "길라잡이관리 " + AdminCommand.RELOAD_REWARD_GROUP);
        socketClient.send(SocketType.CONSOLE_COMMAND, "길라잡이관리 " + AdminCommand.RELOAD_ACHIEVEMENT);
    }
}