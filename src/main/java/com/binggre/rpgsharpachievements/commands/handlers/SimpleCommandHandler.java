package com.binggre.rpgsharpachievements.commands.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SimpleCommandHandler {

    public abstract boolean handle(CommandSender sender, Command command, String label, String[] args);

}