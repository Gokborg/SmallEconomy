package io.github.gokborg.commands;

import org.bukkit.command.CommandSender;

import io.github.gokborg.exceptions.CommandException;

public abstract class SubCommand extends CommandTools
{
	public abstract void execute(CommandSender sender, String[] args) throws CommandException;
}
