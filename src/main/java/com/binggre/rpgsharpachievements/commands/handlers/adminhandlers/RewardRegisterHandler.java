package com.binggre.rpgsharpachievements.commands.handlers.adminhandlers;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroup;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RewardRegisterHandler extends SimpleCommandHandler {

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("/길라잡이관리 보상생성 <이름>");
            return false;
        }
        String name = CommandUtil.space(1, args);
        RewardGroup rewardGroup = RewardGroup.getGroups().get(name);
        if (rewardGroup != null) {
            sender.sendMessage("§c이미 존재하는 보상입니다.");
        } else {
            rewardGroup = new RewardGroup(name);
            rewardGroup.register();
            rewardGroup.write();
            sender.sendMessage(name + " §a보상 그룹이 생성되었습니다.");
            return true;
        }
        return false;
    }
}
