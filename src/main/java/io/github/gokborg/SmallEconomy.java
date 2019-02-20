package io.github.gokborg;

import java.io.IOException;

import io.github.gokborg.commands.AccountCommand;
import io.github.gokborg.commands.AdminCommand;
import io.github.gokborg.commands.PayCommand;
import io.github.gokborg.commands.admin.InterestChecker;
import io.github.gokborg.components.Bank;
import io.github.gokborg.exceptions.CannotCreateAccountException;
import io.github.gokborg.file.Reader;
import io.github.gokborg.file.Writer;
import io.github.pieter12345.javaloader.bukkit.BukkitCommand;
import io.github.pieter12345.javaloader.bukkit.JavaLoaderBukkitProject;

public class SmallEconomy extends JavaLoaderBukkitProject
{
	private Bank bank=new Bank();
	
	@Override
	public void onLoad()
	{
		InterestChecker interestChecker = new InterestChecker(bank);
		interestChecker.runTaskTimer(getPlugin(), 0, 1);
	}
	
	@Override
	public void onUnload()
	{
		try
		{
			Writer.write("plugins/JavaLoader/JavaProjects/SmallEconomy/smalleco.txt", bank);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public String getVersion()
	{
		return "1";
	}
	
	//Get commands method runs before onLoad, fucking remember it idiot
	@Override
	public BukkitCommand[] getCommands()
	{
		try
		{
			bank = Reader.read("plugins/JavaLoader/JavaProjects/SmallEconomy/smalleco.txt");
		}
		catch(IOException | CannotCreateAccountException e1)
		{
			e1.printStackTrace();
		}
		return new BukkitCommand[] {
			new BukkitCommand("pay").setPermission("jl.economy.pay").setUsageMessage("Usage: /pay [from] <to> <amount>").setDescription("Transfer money to other accounts.").setExecutor(new PayCommand(bank)),
			new BukkitCommand("acc").setPermission("jl.economy.account").setUsageMessage("Usage: /acc <create, bal, list, share, unshare>").setDescription("Account mangement commands").setExecutor(new AccountCommand(bank)),
			new BukkitCommand("mngacc").setPermission("jl.economy.mngacc").setUsageMessage("Usage: /mngacc <give>").setDescription("Admin commands for money").setExecutor(new AdminCommand(bank))
		};
	}
}
