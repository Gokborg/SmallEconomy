package io.github.gokborg.commands.acc;

import java.util.Collection;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CommandException;

public class ListAccounts extends SubCommand
{
	private Bank bank;
	
	public ListAccounts(Bank bank)
	{
		this.bank = bank;
	}
	//edit anything
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException
	{
		User player = getUser(bank, getPlayer(sender));
		
		if(args.length == 0) //Print all accounts of this user
		{
			Collection<Account> accounts = player.getAllAccounts();
			check(accounts.isEmpty(), "You have no sub-accounts");
			
			sender.sendMessage(ChatColor.YELLOW + "Your accounts:");
			for(Account account : accounts)
			{
				sender.sendMessage("- " + (account.isShared() ? ChatColor.AQUA : ChatColor.GREEN) + account.getName());
				
			}
			
			List<String> sharedAccounts = player.getSharedAccounts();
			
			if (sharedAccounts.isEmpty())
			{
				sender.sendMessage(ChatColor.YELLOW + "No accounts shared with you.");
				return;
			}
			
			sender.sendMessage(ChatColor.YELLOW + "Shared accounts:");
			for (String sharedAccountName : sharedAccounts)
			{
				sender.sendMessage("- " + ChatColor.LIGHT_PURPLE + sharedAccountName);
			}
			
			//TODO: Print accounts this user also has access to.
		}
		else if(args.length == 1) //Print the accounts of some other user
		{
			User otherUser = bank.getUser(args[0]);
			check(otherUser, "The user '" + args[0] + "' does not exist.");
			
			Collection<Account> accounts = otherUser.getAllAccounts();
			check(accounts.isEmpty(), "The user '" + args[0] + "' has no sub-account.");
			
			sender.sendMessage(args[0] + "'s accounts:");
			for(Account account : accounts)
			{
				sender.sendMessage("- " + ChatColor.GREEN + account.getName());
			}
		}
	}
}
