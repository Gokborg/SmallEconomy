package io.github.gokborg.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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
	
	public abstract List<String> onTabComplete(Player player, String[] args);
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
	{
		try
		{
			return onTabComplete(getPlayer(sender), args);
		}
		catch(CommandException e)
		{
			return Collections.emptyList();
		}
	}
}
