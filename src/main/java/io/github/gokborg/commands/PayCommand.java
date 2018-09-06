package io.github.gokborg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

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
			//TODO: Improve feedback
			sender.sendMessage(ChatColor.RED + "Please first create an account at the central bank ((/acc create) or nag Gok), to use this command.");
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
				sender.sendMessage(ChatColor.GREEN + "Cannot transfer nothing.");
				return true;
			}
		}
		catch(NumberFormatException e)
		{
			sender.sendMessage(ChatColor.RED + "Your last argument has to be an integer value.");
			return true;
		}
		
		if(args.length == 3)
		{
			Account playerAccount = playerUser.getAccount(args[0]);
			
			if(playerAccount == null)
			{
				sender.sendMessage(ChatColor.RED + "You have no account '" + args[0] + "'");
				return true;
			}
			
			// Check if the player has enough money to pay
			if(playerAccount.getTotal() < transactionAmount)
			{
				sender.sendMessage(ChatColor.RED + "Insufficient funds!");
				return true;
			}
			
			// Splitting "name:account" into {name, account}
			String[] otherPlayerInfo = args[1].split(":");
			
			User otherUser = bank.getUser(otherPlayerInfo[0]);
			
			if(otherUser == null)
			{
				sender.sendMessage(ChatColor.RED + "The user '" + otherPlayerInfo[0] + "' has no accounts");
				return true;
			}
			
			Account otherPlayerAccount = bank.getAccount(otherUser, otherPlayerInfo.length == 1 ? otherUser.getName() : otherPlayerInfo[1], otherUser.getUUID());
			
			if(otherPlayerAccount == null)
			{
				sender.sendMessage(ChatColor.RED + "The account '" + otherPlayerInfo[0] + "' does not exist!");
				return true;
			}
			
			//Finally, transfer the money
			playerAccount.remove(transactionAmount);
			otherPlayerAccount.add(transactionAmount);
		}
		else
		{ //Only 2 arguments are left
			//Means they are doing -> /pay <name[:account]> <amount>
			
			Account playerAccount = playerUser.getAccount(sender.getName());
			
			// Check if the player has enough money to pay
			if(playerAccount.getTotal() < transactionAmount)
			{
				sender.sendMessage(ChatColor.RED + "Insufficient funds!");
				return true;
			}
			
			// Splitting "name:account" into {name, account}
			String[] otherPlayerInfo = args[0].split(":");
			
			if(otherPlayerInfo.length == 2)
			{
				// Meaning they typed -> /pay name:account amount
				User otherUser = bank.getUser(otherPlayerInfo[0]);
				
				if(otherUser == null)
				{
					sender.sendMessage(ChatColor.RED + "The user '" + otherPlayerInfo[0] + "' has no accounts");
					return true;
				}
				
				Account otherPlayerAccount = bank.getAccount(otherUser, otherPlayerInfo[1], otherUser.getUUID());
				
				// Check if the account exists
				if(otherPlayerAccount == null)
				{
					sender.sendMessage(ChatColor.RED + "The user '" + otherPlayerInfo[0] + "' has no account '" + otherPlayerInfo[1] + "'");
					return true;
				}
				
				//Finally, transfer the money
				otherPlayerAccount.add(transactionAmount);
				playerAccount.remove(transactionAmount);
			}
			else if(otherPlayerInfo.length == 1)
			{
				// Meaning they typed -> /pay name amount
				
				User otherUser = bank.getUser(otherPlayerInfo[0]);
				
				if(otherUser == null)
				{
					sender.sendMessage(ChatColor.RED + "The user '" + otherPlayerInfo[0] + "' has no accounts");
					return true;
				}
				
				//Adding the amount to the other account.
				Account otherPlayerAccount = bank.getAccount(otherUser, otherUser.getName(), otherUser.getUUID());
				
				//Check if account exists
				if(otherPlayerAccount == null)
				{
					sender.sendMessage(ChatColor.RED + "The account does not exist, this should not have happend, please bugreport to Gokborg");
					return true;
				}
				
				//Finally, transfer the money
				playerAccount.remove(transactionAmount);
				otherPlayerAccount.add(transactionAmount);
			}
		}
		
		//Print positive feedback, nothing has been aborted
		sender.sendMessage(ChatColor.GREEN + "Payment complete!");
		return true;
	}
}
