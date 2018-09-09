package io.github.gokborg.commands;

import org.bukkit.entity.Player;

import io.github.gokborg.exceptions.CommandException;

public abstract class SubCommand extends CommandTools
{
	public abstract void execute(Player player, String[] args) throws CommandException;
}
