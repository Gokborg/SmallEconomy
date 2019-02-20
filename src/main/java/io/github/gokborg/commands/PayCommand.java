package io.github.gokborg.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.AccountNotFoundException;
import io.github.gokborg.exceptions.CommandException;

public class PayCommand extends CommandWrapper
{
	private final Bank bank;
	
	public PayCommand(Bank bank)
	{
		this.bank = bank;
	}
	
	public boolean execute(final CommandSender sender, String[] args) throws CommandException
	{
		//Check if sender is a player and has a useraccount
		User playerUser = getUser(bank, getPlayer(sender));
		
		//Only allow 2 or 3 arguments
		if(args.length != 2 && args.length != 3)
		{
			return false;
		}
		
		Long transactionAmount = null;
		try
		{
			//Parse last argument to Integer
			transactionAmount = Long.parseLong(args[args.length - 1]);
			check(transactionAmount < 0, "You can only transfer positive amounts.");
			check(transactionAmount == 0, "Cannot transfer nothing.");
		}
		catch(NumberFormatException e)
		{
			die("Your last argument has to be an integer value.");
		}
		
		try
		{
			String msg;
			if(args.length == 3)
			{
				Account playerAccount = playerUser.getAccount(args[0]);
				
				if(playerAccount == null)
				{
					playerAccount = bank.searchAccount(args[0]);
				}
				if(playerAccount == null)
				{
					playerAccount = bank.parseAccountID(args[0]);
				}
				
				check(!playerAccount.hasAccess(playerUser), "You have no permission to transfer from this account.");
				
				//Check if the player has enough money to pay
				check(playerAccount.getTotal() < transactionAmount, "Insufficient funds.");
				
				Account otherPlayerAccount = bank.parseAccountID(args[1]);
				
				//Finally, transfer the money
				playerAccount.remove(transactionAmount);
				otherPlayerAccount.add(transactionAmount);
				msg = ChatColor.GREEN + "Recieved " + transactionAmount + "☕ from " + sender.getName().toLowerCase() + ":" + playerAccount.getName() + ".";
				trySend(bank.parseAccountID(args[1]), msg);
			}
			else //Only 2 arguments are left
			{
				Account playerAccount = playerUser.getMainAccount();
				
				// Check if the player has enough money to pay
				check(playerAccount.getTotal() < transactionAmount, "Insufficient funds.");
				
				Account otherPlayerAccount = bank.parseAccountID(args[0]);
				
				//Finally, transfer the money
				playerAccount.remove(transactionAmount);
				otherPlayerAccount.add(transactionAmount);
				msg = ChatColor.GREEN + "Recieved " + transactionAmount + "☕ from " + sender.getName().toLowerCase() + ".";
				trySend(bank.parseAccountID(args[0]), msg);
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
	

	@Override
	public List<String> onTabComplete(Player player, String[] args)
	{
		if(args.length == 1)
		{
			return TabCompleteTools.closestUserWithAccount(bank, args[0]);
		}
		
		if(args.length == 2 && !args[1].matches("[0-9]+"))
		{
			return TabCompleteTools.closestUserWithAccount(bank, args[1]);
		}
		
		return Collections.emptyList();
	}
	
}
