package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class ShareAccount extends SubCommand
{
	private Bank bank;
	
	public ShareAccount(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void process(CommandSender sender, String[] args)
	{
		User player = bank.getUser(((Player) sender).getUniqueId());
		if(player == null)
		{
			sender.sendMessage(ChatColor.RED + "Please first create an account '/acc create', to use this command.");
			return;
		}
		
		if(args.length != 2)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /acc share <account> <user>");
		}
		else
		{
			//If they attempt to share w/ themselves stop them.
			if(args[1].equalsIgnoreCase(sender.getName()))
			{
				sender.sendMessage(ChatColor.RED + "You can not share an account with yourself.");
				return;
			}
			
			//Shares with the other user
			User otherUser = bank.getUser(args[1].toLowerCase());
			
			//Incase the otheruser does not exist
			if(otherUser == null)
			{
				sender.sendMessage(ChatColor.RED + "User '" + args[1] + "' does not exist.");
				return;
			}
			
			Account account = player.getAccount(args[0]);
			if(player.getAccount(args[0]) == null)
			{
				sender.sendMessage(ChatColor.RED + "The account you are trying to share does not exist.");
				return;
			}
			
			account.addUser(otherUser);
			sender.sendMessage(ChatColor.GREEN + "Successfully shared '" + args[0] + "' with " + args[1]);
		}
	}
}
