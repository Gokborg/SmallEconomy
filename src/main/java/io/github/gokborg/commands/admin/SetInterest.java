package io.github.gokborg.commands.admin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Bank;
import io.github.gokborg.exceptions.CommandException;

public class SetInterest extends SubCommand
{
	private Bank bank;
	
	public SetInterest(Bank bank)
	{
		this.bank = bank;
	}

	@Override
	public void execute(Player player, String[] args) throws CommandException
	{
		check(args.length != 2, "Usage: /mngacc interest set [rate] [time (s)]");
		try
		{
			bank.setInterestRate(Double.parseDouble(args[0]));
			try
			{
				bank.setInterestTime(Math.round(Long.parseLong(args[1])/1000));
			}
			catch(NumberFormatException e)
			{
				player.sendMessage(ChatColor.RED + "Please use a whole number for the second arguement");
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage(ChatColor.RED + "Please use a decimal value for the first arguement");
		}
	}

	@Override
	public List<String> tabComplete(Player player, String[] args)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
