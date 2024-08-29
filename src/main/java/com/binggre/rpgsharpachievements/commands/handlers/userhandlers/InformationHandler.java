package com.binggre.rpgsharpachievements.commands.handlers.userhandlers;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.objects.PlayerAchievement;
import com.binggre.rpgsharpachievements.objects.PlayerAchievementLoader;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.ItemUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class InformationHandler extends SimpleCommandHandler {

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c플레이어 전용 명령어 입니다.");
            return false;
        }
        PlayerAchievement playerAchievement = PlayerAchievementLoader.getInstance().get(player);
        Achievement now = playerAchievement.getNow();
        if (now == null) {
            player.sendMessage("§c더 이상 진행할 길라잡이가 없습니다.");
            return false;
        }
        List<String> description = now.getDescription();
        int playerAmount = playerAchievement.getAmount();
        int achieveAmount = now.getAmount();
        ItemUtil.replaceLore(description, "<player_amount>", String.valueOf(playerAmount));
        ItemUtil.replaceLore(description, "<object_amount>", String.valueOf(achieveAmount));
        ItemUtil.replaceLore(description, "<player_level>", String.valueOf(playerAchievement.getLevel()));

        description.forEach(player::sendMessage);
        return true;
    }
}