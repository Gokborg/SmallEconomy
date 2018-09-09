package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.AccountNotFoundException;

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
			
			try
			{
				//Shares with the other user
				User otherUser = bank.getUser(args[1].toLowerCase());
				
				//Incase the otheruser does not exist
				if(otherUser == null)
				{
					sender.sendMessage(ChatColor.RED + "User '" + args[1] + "' does not exist.");
					return;
				}
				
				if(player.getAccount(args[0]) == null)
				{
					sender.sendMessage(ChatColor.RED + "The account you are trying to share does not exist.");
				}
				
				player.shareAccount(otherUser, args[0]);
				sender.sendMessage(ChatColor.GREEN + "Successfully shared '" + args[0] + "' with " + args[1]);
			}
			catch(AccountNotFoundException e)
			{
				sender.sendMessage(ChatColor.RED + "The account '" + args[0] + "' does not exist.");
				return;
			}
		}
	}
}
