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
			new BukkitCommand("pay").setPermission("jl.economy.pay").setUsageMessage("Usage: /pay [from] <to> <amount>").setDescription("Transfer money to other accounts.").setExecutor(new PayCommand(bank)),
			new BukkitCommand("acc").setPermission("jl.economy.account").setUsageMessage("Usage: /acc <create , bal, list, share, unshare>").setDescription("Account mangement commands").setExecutor(new AccountCommand(bank)),
			new BukkitCommand("mngacc").setPermission("jl.economy.mngacc").setUsageMessage("Usage: /mngacc <give>").setDescription("Admin commands for money").setExecutor(new AdminCommand(bank))
		};
	}
}
