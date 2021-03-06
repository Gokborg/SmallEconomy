package io.github.gokborg.commands.admin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.commands.TabCompleteTools;
import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.exceptions.AccountNotFoundException;
import io.github.gokborg.exceptions.CommandException;

public class GiveMoney extends SubCommand
{
	private final Bank bank;
	
	public GiveMoney(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void execute(Player player, String[] args) throws CommandException
	{
		// /accmng give Gokborg [+ or - or =] <amount>
		
		check(args.length != 3, "Usage: /mngacc give <player> <+ or - or => <amount>");
		
		try
		{
			Account otherAccount = bank.parseAccountID(args[0]);
			long amount = Long.parseLong(args[2]);
			
			switch(args[1])
			{
			case "+":
				otherAccount.add(amount);
				break;
			case "-":
				otherAccount.remove(amount);
				break;
			case "=":
				otherAccount.setTotal(amount);
				break;
			default:
				player.sendMessage(ChatColor.RED + "Usage: /mngacc give <player> <+ or - or => <amount>");
			}
			
			player.sendMessage(ChatColor.GREEN + "Added " + amount + "☕ to " + args[0] + "'s account");
		}
		catch(AccountNotFoundException e)
		{
			player.sendMessage("acc not found");
			player.sendMessage(ChatColor.RED + e.getMessage());
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "Please use a integer for the amount.");
		}
	}
	
	@Override
	public List<String> tabComplete(Player player, String[] args)
	{
		if(args.length == 1)
		{
			return TabCompleteTools.closestUserWithAccount(bank, args[0]);
		}
		
		if(args.length == 2)
		{
			return Arrays.asList("+", "-", "=");
		}
		
		return Collections.emptyList();
	}
}
