package io.github.gokborg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.AccountNotFoundException;

public class PayCommand implements CommandExecutor
{
	private final Bank bank;
	
	public PayCommand(Bank bank)
	{
		this.bank = bank;
	}
	
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args)
	{
		//Check if sender is a player, only players have accounts and may do transactions
		if(!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "You must be a player to run this command.");
			return true;
		}
		
		User playerUser = bank.getUser(((Player) sender).getUniqueId());
		if(playerUser == null)
		{
			sender.sendMessage(ChatColor.RED + "Please first create an account '/acc create', to use this command.");
			return true;
		}
		
		//Only allow 2 or 3 arguments
		if(args.length != 2 && args.length != 3)
		{
			return false;
		}
		
		Integer transactionAmount;
		try
		{
			//Parse last argument to Integer
			transactionAmount = Integer.parseInt(args[args.length - 1]);
			if(transactionAmount < 0)
			{
				sender.sendMessage(ChatColor.RED + "You can only transfer positive amounts.");
				return true;
			}
			else if(transactionAmount == 0)
			{
				sender.sendMessage(ChatColor.RED + "Cannot transfer nothing.");
				return true;
			}
		}
		catch(NumberFormatException e)
		{
			sender.sendMessage(ChatColor.RED + "Your last argument has to be an integer value.");
			return true;
		}
		
		try
		{
			if(args.length == 3)
			{
				Account playerAccount = bank.parseAccountID(args[0]);
				
				if(!playerAccount.hasAccess(playerUser))
				{
					sender.sendMessage(ChatColor.RED + "You have no permission to transfer from this account.");
					return true;
				}
				
				//Check if the player has enough money to pay
				if(playerAccount.getTotal() < transactionAmount)
				{
					sender.sendMessage(ChatColor.RED + "Insufficient funds.");
					return true;
				}
				
				Account otherPlayerAccount = bank.parseAccountID(args[1]);
				
				//Finally, transfer the money
				playerAccount.remove(transactionAmount);
				otherPlayerAccount.add(transactionAmount);
			}
			else //Only 2 arguments are left
			{
				Account playerAccount = playerUser.getMainAccount();
				
				// Check if the player has enough money to pay
				if(playerAccount.getTotal() < transactionAmount)
				{
					sender.sendMessage(ChatColor.RED + "Insufficient funds.");
					return true;
				}
				
				Account otherPlayerAccount = bank.parseAccountID(args[0]);
				
				//Finally, transfer the money
				playerAccount.remove(transactionAmount);
				otherPlayerAccount.add(transactionAmount);
			}
			
			//Print positive feedback, nothing has been aborted
			sender.sendMessage(ChatColor.GREEN + "Payment complete.");
		}
		catch(AccountNotFoundException e)
		{
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}
		
		return true;
	}
}
