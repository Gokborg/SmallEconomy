package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
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
	public void execute(CommandSender sender, String[] args) throws CommandException
	{
		User player = getUser(bank, getPlayer(sender));
		
		if(args.length == 0)
		{
			sender.sendMessage(ChatColor.GREEN + "Balance: " + player.getMainAccount().getTotal() + "✿");
		}
		
		else if(args.length == 1)
		{
			try
			{
				Account playerAccount = player.getAccount(args[0]);
				
				if(playerAccount == null)
				{
					playerAccount = bank.parseAccountID(args[0]);
				}
				
				check(!playerAccount.hasAccess(player), "You don't have permission to access this account.");
				
				sender.sendMessage(ChatColor.GREEN + "Balance: " + playerAccount.getTotal() + "✿");
				
			}
			catch(AccountNotFoundException e)
			{
				sender.sendMessage(ChatColor.RED + e.getMessage());
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "Usage: /acc bal [account]");
		}
	}
}
