package com.binggre.rpgsharpachievements.commands.handlers.adminhandlers;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroup;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroupGUI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RewardEditorHandler extends SimpleCommandHandler {

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c플레이어 전용 명령어 입니다.");
            return false;
        }
        if (args.length < 2) {
            player.sendMessage("/길라잡이관리 보상설정 <이름>");
            return false;
        }
        String groupName = CommandUtil.space(1, args);
        RewardGroup rewardGroup = RewardGroup.getGroups().get(groupName);
        if (rewardGroup == null) {
            player.sendMessage("§c존재하지 않는 보상 그룹입니다.");
            return false;
        }
        Inventory inventory = new RewardGroupGUI(groupName).getInventory();
        player.openInventory(inventory);
        return true;
    }
}
