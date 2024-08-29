package com.binggre.rpgsharpachievements.commands;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.commands.handlers.adminhandlers.*;
import com.binggre.rpgsharpachievements.objects.achievements.Achievement;
import com.binggre.rpgsharpachievements.objects.itemgroups.ItemGroup;
import com.binggre.rpgsharpachievements.objects.reward.RewardGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class AdminCommand implements CommandExecutor, TabCompleter {

    private final SimpleCommandHandler groupRegisterHandler = new GroupRegisterHandler();
    private final SimpleCommandHandler groupUnregisterHandler = new GroupUnregisterHandler();
    private final SimpleCommandHandler groupEditorHandler = new GroupEditorHandler();
    private final SimpleCommandHandler levelEditHandler = new PlayerLevelEditHandler();
    private final SimpleCommandHandler rewardRegisterHandler = new RewardRegisterHandler();
    private final SimpleCommandHandler rewardUnregisterHandler = new RewardUnregisterHandler();
    private final SimpleCommandHandler rewardEditorHandler = new RewardEditorHandler();

    public static final String RELOAD_ITEM_GROUP = "리로드그룹";
    public static final String RELOAD_REWARD_GROUP = "리로드보상그룹";
    public static final String RELOAD_ACHIEVEMENT = "리로드과제";

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!sender.isOp()) {
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage("");
            sender.sendMessage("/길라잡이관리 그룹생성 <이름>");
            sender.sendMessage("/길라잡이관리 그룹삭제 <이름>");
            sender.sendMessage("/길라잡이관리 그룹설정 <이름>");
            sender.sendMessage("");
            sender.sendMessage("/길라잡이관리 보상생성 <이름>");
            sender.sendMessage("/길라잡이관리 보상삭제 <이름>");
            sender.sendMessage("/길라잡이관리 보상설정 <이름>");
            sender.sendMessage("");
            sender.sendMessage("/길라잡이관리 레벨설정 <닉네임> <레벨>");
            sender.sendMessage("");
            return false;
        }
        switch (args[0]) {
            case "그룹생성" -> {
                return groupRegisterHandler.handle(sender, command, label, args);
            }
            case "그룹삭제" -> {
                return groupUnregisterHandler.handle(sender, command, label, args);
            }
            case "그룹설정" -> {
                return groupEditorHandler.handle(sender, command, label, args);
            }
            case "레벨설정" -> {
                return levelEditHandler.handle(sender, command, label, args);
            }
            case "보상생성" -> {
                return rewardRegisterHandler.handle(sender, command, label, args);
            }
            case "보상삭제" -> {
                return rewardUnregisterHandler.handle(sender, command, label, args);
            }
            case "보상설정" -> {
                return rewardEditorHandler.handle(sender, command, label, args);
            }
            case RELOAD_ITEM_GROUP -> {
                ItemGroup.registerAll();
            }
            case RELOAD_ACHIEVEMENT -> {
                Achievement.registerAll();
            }
            case RELOAD_REWARD_GROUP -> {
                RewardGroup.registerAll();
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("그룹생성", "그룹삭제", "그룹설정", "보상생성", "보상설정", "보상삭제", "레벨설정");
        }
        switch (args[0]) {
            case "그룹삭제", "그룹설정" -> {
                return ItemGroup.getGroups().keySet().stream().toList();
            }
            case "보상삭제", "보상설정" -> {
                return RewardGroup.getGroups().keySet().stream().toList();
            }
        }
        return null;
    }
}