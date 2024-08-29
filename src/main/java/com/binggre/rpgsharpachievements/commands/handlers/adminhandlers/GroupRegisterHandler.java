package com.binggre.rpgsharpachievements.commands.handlers.adminhandlers;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.objects.itemgroups.ItemGroup;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GroupRegisterHandler extends SimpleCommandHandler {

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("/길라잡이관리 그룹생성 <이름>");
            return false;
        }
        String groupName = CommandUtil.space(1, args);
        ItemGroup itemGroup = ItemGroup.getGroups().get(groupName);
        if (itemGroup != null) {
            sender.sendMessage("§c이미 존재하는 그룹입니다.");
        } else {
            itemGroup = new ItemGroup(groupName);
            itemGroup.register();
            itemGroup.write();
            sender.sendMessage(groupName + " 그룹을 생성했습니다.");
            sender.sendMessage("/길라잡이관리 그룹설정 <이름>");
            return true;
        }
        return false;
    }
}
