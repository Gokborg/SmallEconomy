package io.github.gokborg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CommandException;

public abstract class CommandTools
{
	protected void die(String message) throws CommandException
	{
		throw new CommandException(ChatColor.RED + message);
	}
	
	protected void checkNotNull(Object obj, String failtureMessage) throws CommandException
	{
		if(obj == null)
		{
			die(failtureMessage);
		}
	}
	
	protected Player getPlayer(CommandSender sender) throws CommandException
	{
		if(!(sender instanceof Player))
		{
			die("You must be a player to run this command.");
		}
		
		return (Player) sender;
	}
	
	protected User getUser(Bank bank, Player player) throws CommandException
	{
		User user = bank.getUser(player.getUniqueId());
		if(user == null)
		{
			die("Please first create an account '/acc create', to use this command.");
		}
		
		return user;
	}
}
