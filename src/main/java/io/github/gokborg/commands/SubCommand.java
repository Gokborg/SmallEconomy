package io.github.gokborg.commands;

import java.util.List;

import org.bukkit.entity.Player;

import io.github.gokborg.exceptions.CommandException;

public abstract class SubCommand extends CommandTools
{
	public abstract void execute(Player player, String[] args) throws CommandException;
	
	public abstract List<String> tabComplete(Player player, String[] args);
}
