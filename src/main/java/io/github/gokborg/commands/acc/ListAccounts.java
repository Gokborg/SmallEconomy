package io.github.gokborg.commands.acc;

import java.util.Collection;

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
		
		if(args.length == 0) //Print all accounts of this user
		{
			Collection<Account> accounts = player.getAllAccounts();
			if(accounts.isEmpty())
			{
				sender.sendMessage(ChatColor.GREEN + "You don't have any sub-account.");
				return;
			}
			
			sender.sendMessage(ChatColor.YELLOW + "Your accounts:");
			for(Account account : accounts)
			{
				sender.sendMessage("- " + (account.isShared() ? ChatColor.AQUA : ChatColor.GREEN) + account.getName());
				
			}
			sender.sendMessage(ChatColor.YELLOW + "Shared accounts:");
			for (String sharedAccountName : player.getSharedAccounts())
			{
				sender.sendMessage("- " + ChatColor.LIGHT_PURPLE + sharedAccountName);
			}
			
			//TODO: Print accounts this user also has access to.
		}
		else if(args.length == 1) //Print the accounts of some other user
		{
			User otherUser = bank.getUser(args[0]);
			if(otherUser == null)
			{
				sender.sendMessage(ChatColor.RED + "The user '" + args[0] + "' does not exist.");
				return;
			}
			
			Collection<Account> accounts = otherUser.getAllAccounts();
			if(accounts.isEmpty())
			{
				sender.sendMessage(ChatColor.GREEN + "The user '" + args[0] + "' has no sub-account.");
				return;
			}
			
			sender.sendMessage(args[0] + "'s accounts:");
			for(Account account : accounts)
			{
				sender.sendMessage("- " + ChatColor.GREEN + account.getName());
			}
		}
	}
}
