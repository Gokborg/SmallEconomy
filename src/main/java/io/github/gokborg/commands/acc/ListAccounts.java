package io.github.gokborg.commands.acc;

import java.util.Collection;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
	
	@Override
	public void execute(Player player, String[] args) throws CommandException
	{
		User user = getUser(bank, player);
		
		if(args.length == 0) //Print all accounts of this user
		{
			Collection<Account> accounts = user.getAllAccounts();
			check(accounts.isEmpty(), "You have no sub-accounts");
			
			player.sendMessage(ChatColor.YELLOW + "Your accounts:");
			for(Account account : accounts)
			{
				player.sendMessage("- " + (account.isShared() ? ChatColor.AQUA : ChatColor.GREEN) + account.getName());
				
			}
			
			List<String> sharedAccounts = user.getSharedAccounts();
			
			if(sharedAccounts.isEmpty())
			{
				player.sendMessage(ChatColor.YELLOW + "No accounts shared with you.");
				return;
			}
			
			player.sendMessage(ChatColor.YELLOW + "Shared accounts:");
			for(String sharedAccountName : sharedAccounts)
			{
				player.sendMessage("- " + ChatColor.LIGHT_PURPLE + sharedAccountName);
			}
			
			//TODO: Print accounts this user also has access to.
		}
		else if(args.length == 1) //Print the accounts of some other user
		{
			User otherUser = bank.getUser(args[0]);
			check(otherUser, "The user '" + args[0] + "' does not exist.");
			
			Collection<Account> accounts = otherUser.getAllAccounts();
			check(accounts.isEmpty(), "The user '" + args[0] + "' has no sub-account.");
			
			player.sendMessage(args[0] + "'s accounts:");
			for(Account account : accounts)
			{
				player.sendMessage("- " + ChatColor.GREEN + account.getName());
			}
		}
	}
}
