package io.github.gokborg.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
		
		Integer transactionAmount = null;
		try
		{
			//Parse last argument to Integer
			transactionAmount = Integer.parseInt(args[args.length - 1]);
			check(transactionAmount < 0, "You can only transfer positive amounts.");
			check(transactionAmount == 0, "Cannot transfer nothing.");
		}
		catch(NumberFormatException e)
		{
			die("Your last argument has to be an integer value.");
		}
		
		try
		{
			if(args.length == 3)
			{
				Account playerAccount = playerUser.getAccount(args[0]);
				
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
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
	{
		if(args.length == 1)
		{
			List<String> tabCompleteList = new ArrayList<>();
			
			int colonIndex = args[0].indexOf(':');
			
			for(String userStr : bank.getAllUsers())
			{
				if(colonIndex != -1)
				{
					User user = bank.getUser(args[0].substring(0, colonIndex));
					if(user != null)
					{
						for(String accStr : user.getAllAccountsName())
						{
							if(accStr.startsWith(args[0].substring(colonIndex + 1, args[0].length())))
							{
								tabCompleteList.add(user.getName().toLowerCase() + ":" + accStr);
							}
						}
					}
				}
				else if(!args[0].isEmpty() && userStr.startsWith(args[0]))
				{
					
					tabCompleteList.add(userStr);
					return tabCompleteList;
				}
			}
			return bank.getAllUsers();
		}
		return Collections.emptyList();
	}
}
