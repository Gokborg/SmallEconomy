package io.github.gokborg;

import io.github.gokborg.commands.AccountCommand;
import io.github.gokborg.commands.PayCommand;
import io.github.gokborg.components.Bank;
import io.github.pieter12345.javaloader.bukkit.BukkitCommand;
import io.github.pieter12345.javaloader.bukkit.JavaLoaderBukkitProject;

public class SmallEconomy extends JavaLoaderBukkitProject
{
	private Bank bank = new Bank();
	
	@Override
	public void onLoad()
	{
	}
	
	@Override
	public String getVersion()
	{
		return "1";
	}
	
	@Override
	public BukkitCommand[] getCommands()
	{
		return new BukkitCommand[] {
			new BukkitCommand("pay", "Transfer money to other accounts.", "Usage: /pay [from] <to> <amount>.", "jl.economy.pay", (String[]) null, new PayCommand(bank), null),
			new BukkitCommand("acc", "Account mangement commands", "Usage: /acc <create , balance, list> ...", "jl.economy.account", (String[]) null, new AccountCommand(bank), null)
		};
	}
}
