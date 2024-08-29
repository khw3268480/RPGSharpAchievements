package com.binggre.rpgsharpachievements.commands.handlers.adminhandlers;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroup;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RewardUnregisterHandler extends SimpleCommandHandler {

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("/길라잡이관리 보상삭제 <이름>");
            return false;
        }
        String groupName = CommandUtil.space(1, args);
        RewardGroup rewardGroup = RewardGroup.getGroups().get(groupName);
        if (rewardGroup == null) {
            sender.sendMessage("§c존재하지 않는 그룹입니다.");
        } else {
            rewardGroup.unregister();
            sender.sendMessage("§c" + groupName + " 보상 그룹을 삭제했습니다.");
        }
        return false;
    }
}
