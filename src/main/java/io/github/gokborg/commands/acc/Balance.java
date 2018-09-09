package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.AccountNotFoundException;

public class Balance extends SubCommand
{	
	private final Bank bank;
	
	public Balance(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void process(CommandSender sender, String[] args)
	{
		User player = bank.getUser(((Player)sender).getUniqueId());
		if (player == null) 
		{
			sender.sendMessage(ChatColor.RED + "Please first create an account '/acc create', to use this command.");
			return;
		}
		
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.GREEN + "Balance: " + player.getMainAccount().getTotal() + "✿");
		}
		
		else if (args.length == 1) 
		{
			try
			{
				Account playerAccount = bank.parseAccountID(args[0], player);
				if (playerAccount.hasAccess(player))
				{
					sender.sendMessage(ChatColor.GREEN + "Balance: " + playerAccount.getTotal() + "✿");
				}
				else 
				{
					sender.sendMessage(ChatColor.RED + "You don't have permission to access this account.");
				}
			}
			catch(AccountNotFoundException e)
			{
				sender.sendMessage(ChatColor.RED + e.getMessage());
			}
		}
		else 
		{
			sender.sendMessage(ChatColor.RED + "The correct usage is '/acc bal [account]'");
		}	
	}
}
