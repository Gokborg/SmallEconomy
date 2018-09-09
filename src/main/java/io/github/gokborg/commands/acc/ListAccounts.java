package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class ListAccounts extends SubCommand
{
	private Bank bank;
	
	public ListAccounts(Bank bank)
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
		
		//TODO: Make the message sent look nicer its pretty ugly...
		
		if(args.length == 0)
		{
			//Means they want to know all their accounts
			
			sender.sendMessage("\nAccounts:\n" + "=========");
			sender.sendMessage(ChatColor.GREEN + player.getName());
			for(Account account : player.getAllAccounts())
			{
				sender.sendMessage(ChatColor.GREEN + account.getName());
			}
		}
		else if(args.length == 1)
		{
			//Means they want to find out about the accounts of another user
			
			User otherUser = bank.getUser(args[0]);
			if(otherUser == null)
			{
				sender.sendMessage(ChatColor.RED + "The user '" + args[0] + "' does not exist.");
				return;
			}
			
			sender.sendMessage("\nAccounts:\n" + "=========");
			sender.sendMessage(ChatColor.GREEN + otherUser.getName());
			for(Account account : otherUser.getAllAccounts())
			{
				sender.sendMessage(ChatColor.GREEN + account.getName());
			}
		}
	}
}
