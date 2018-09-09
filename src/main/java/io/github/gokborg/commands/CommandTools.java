package io.github.gokborg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CommandException;

public abstract class CommandTools
{
	protected void die(String message) throws CommandException
	{
		throw new CommandException(ChatColor.RED + message);
	}
	
	protected void check(Object obj, String failtureMessage) throws CommandException
	{
		if(obj == null)
		{
			die(failtureMessage);
		}
	}
	
	protected void check(boolean bool, String failtureMessage) throws CommandException
	{
		if(bool)
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
	
	protected Account getAccount(User user, String accountName) throws CommandException
	{
		Account account = user.getAccount(accountName);
		
		if(account == null)
		{
			die("The account '" + accountName + "' does not exist.");
		}
		
		return account;
	}
}
