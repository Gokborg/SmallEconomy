package io.github.gokborg;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.gokborg.commands.AccountCommand;
import io.github.gokborg.commands.AdminCommand;
import io.github.gokborg.commands.PayCommand;
import io.github.gokborg.commands.admin.InterestChecker;
import io.github.gokborg.components.Bank;
import io.github.gokborg.exceptions.CannotCreateAccountException;
import io.github.gokborg.file.Reader;
import io.github.gokborg.file.Saver;
import io.github.gokborg.file.Writer;

//TODO: Add subcommand to rename a sub-account
public class SmallEconomy extends JavaPlugin
{
	private Bank bank;
	
	@Override
	public void onEnable()
	{
		try
		{
			bank = Reader.read("plugins/SmallEconomy/smalleco.txt");
		}
		catch(IOException | CannotCreateAccountException e1)
		{
			System.out.println("Couldn't find the smalleco.txt file! Going to make one!");
			boolean success = new File("plugins/SmallEconomy").mkdir();
			if(!success)
			{
				this.getLogger().log(Level.SEVERE, "Failed to make the SmallEconomy directory!");
			}
			bank = new Bank();
		}
		this.getCommand("pay").setExecutor(new PayCommand(bank));
		this.getCommand("acc").setExecutor(new AccountCommand(bank));
		this.getCommand("mngacc").setExecutor(new AdminCommand(bank));
		
		InterestChecker interestChecker = new InterestChecker(bank);
		interestChecker.runTaskTimer(this, 0, 20);
		Saver saver = new Saver(this);
		saver.runTaskTimerAsynchronously(this, 0, 20);
	}
	
	@Override
	public void onDisable()
	{
		saveEconomy();
	}
	
	public void saveEconomy()
	{
		try
		{
			Writer.write("plugins/SmallEconomy/smalleco.txt", bank);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
