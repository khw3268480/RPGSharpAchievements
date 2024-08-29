package com.binggre.rpgsharpachievements.commands;

import com.binggre.rpgsharpachievements.commands.handlers.SimpleCommandHandler;
import com.binggre.rpgsharpachievements.commands.handlers.userhandlers.CompletionHandler;
import com.binggre.rpgsharpachievements.commands.handlers.userhandlers.InformationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class UserCommand implements CommandExecutor, TabCompleter {

    private final SimpleCommandHandler informationHandler = new InformationHandler();
    private final SimpleCommandHandler completionHandler = new CompletionHandler();

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) {
            player.sendMessage("");
            player.sendMessage("/길라잡이 완수");
            player.sendMessage("/길라잡이 확인");
            player.sendMessage("");
            return false;
        }
        String subCommand = args[0];
        switch (subCommand) {
            case "완수" -> {
                return completionHandler.handle(player, command, label, args);
            }
            case "확인" -> {
                return informationHandler.handle(player, command, label, args);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("완수", "확인");
        }
        return null;
    }
}