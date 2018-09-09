package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.AccountNotFoundException;
import io.github.gokborg.exceptions.CommandException;

public class Balance extends SubCommand
{
	private final Bank bank;
	
	public Balance(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void execute(Player player, String[] args) throws CommandException
	{
		User user = getUser(bank, player);
		
		if(args.length == 0)
		{
			player.sendMessage(ChatColor.GREEN + "Balance: " + user.getMainAccount().getTotal() + "✿");
		}
		else if(args.length == 1)
		{
			try
			{
				Account playerAccount = user.getAccount(args[0]);
				
				if(playerAccount == null)
				{
					playerAccount = bank.parseAccountID(args[0]);
				}
				
				check(!playerAccount.hasAccess(user), "You don't have permission to access this account.");
				
				player.sendMessage(ChatColor.GREEN + "Balance: " + playerAccount.getTotal() + "✿");
			}
			catch(AccountNotFoundException e)
			{
				player.sendMessage(ChatColor.RED + e.getMessage());
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "Usage: /acc bal [account]");
		}
	}
}
