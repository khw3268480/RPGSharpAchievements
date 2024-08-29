package com.binggre.rpgsharpachievements.commands.handlers.adminhandlers;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.objects.itemgroups.ItemGroup;
import com.binggre.rpgsharpachievements.objects.itemgroups.ItemGroupGUI;
import com.hj.rpgsharp.rpg.apis.rpgsharp.utils.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GroupEditorHandler extends SimpleCommandHandler {

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c플레이어 전용 명령어 입니다.");
            return false;
        }
        if (args.length < 2) {
            player.sendMessage("/길라잡이관리 그룹설정 <이름>");
            return false;
        }
        String groupName = CommandUtil.space(1, args);
        ItemGroup itemGroup = ItemGroup.getGroups().get(groupName);
        if (itemGroup == null) {
            player.sendMessage("§c존재하지 않는 그룹입니다.");
            return false;
        }
        Inventory inventory = new ItemGroupGUI(groupName).getInventory();
        player.openInventory(inventory);
        return true;
    }
}
