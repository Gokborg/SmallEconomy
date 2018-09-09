package io.github.gokborg.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import io.github.gokborg.exceptions.CommandException;

public abstract class CommandWrapper extends CommandTools implements CommandExecutor, TabCompleter
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		try
		{
			return execute(sender, args);
		}
		catch(CommandException e)
		{
			sender.sendMessage(e.getMessage());
			return true;
		}
	}
	
	protected abstract boolean execute(CommandSender sender, String[] args) throws CommandException;
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
	{
		return Collections.emptyList();
	}
}
