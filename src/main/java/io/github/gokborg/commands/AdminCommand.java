package io.github.gokborg.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.gokborg.components.Bank;
import io.github.gokborg.exceptions.CommandException;

public class AdminCommand extends CommandWrapper implements CommandExecutor
{
	private final Bank bank;
	public AdminCommand(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	protected boolean execute(CommandSender sender, String[] args) throws CommandException
	{
		
		
		return false;
	}

	
}
