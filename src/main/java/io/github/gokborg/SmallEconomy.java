package io.github.gokborg;

import org.bukkit.Bukkit;

import io.github.gokborg.commands.AccountCommand;
import io.github.gokborg.commands.AdminCommand;
import io.github.gokborg.commands.PayCommand;
import io.github.gokborg.components.Bank;
import io.github.pieter12345.javaloader.bukkit.BukkitCommand;
import io.github.pieter12345.javaloader.bukkit.JavaLoaderBukkitProject;

public class SmallEconomy extends JavaLoaderBukkitProject
{
	private Bank bank = new Bank();
	
	//TODO: DELETE THIS
	@SuppressWarnings("deprecation")
	@Override
	public void onLoad()
	{
		//TODO: DELETE THIS
		bank.createUser("Ecconia", Bukkit.getOfflinePlayer("Ecconia").getUniqueId());
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
			new BukkitCommand("acc", "Account mangement commands", "Usage: /acc <create , bal, list, share, unshare>", "jl.economy.account", (String[]) null, new AccountCommand(bank), null),
			new BukkitCommand("accmgm", "Admin commands for money", "Usage: /accmgm <give>", "jl.economy.accmgm", (String[]) null, new AdminCommand(bank), null)
		};
	}
}
