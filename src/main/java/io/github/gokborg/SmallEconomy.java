package io.github.gokborg;

import io.github.gokborg.commands.AccountCommand;
import io.github.gokborg.commands.PayCommand;
import io.github.gokborg.components.Bank;
import io.github.pieter12345.javaloader.bukkit.BukkitCommand;
import io.github.pieter12345.javaloader.bukkit.JavaLoaderBukkitProject;

public class SmallEconomy extends JavaLoaderBukkitProject{
	
	private Bank bank;
	
	@Override
	public void onLoad() {
		
		//Load in a bank from a file if there is a bank file available
		bank = new Bank();
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "0.0.1-SNAPSHOT";
	}
	
	@Override
	public BukkitCommand[] getCommands() {
		return new BukkitCommand[] {
				new BukkitCommand("pay", "Pay a player a specific amount.", "Usage: /pay [account] <account> <amount>.",
						"jl.economy.pay", (String[])null, new PayCommand(bank), null),
				new BukkitCommand("acc", "Account command", "Usage: /acc <create> ...",
						"jl.economy.pay", (String[])null, new AccountCommand(bank), null)
				
			};
	}

}
