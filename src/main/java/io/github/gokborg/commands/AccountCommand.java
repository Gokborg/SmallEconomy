package io.github.gokborg.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.acc.Balance;
import io.github.gokborg.commands.acc.CreateAccount;
import io.github.gokborg.commands.acc.ListAccounts;
import io.github.gokborg.commands.acc.ShareAccount;
import io.github.gokborg.commands.acc.SubCommand;
import io.github.gokborg.commands.acc.UnshareAccount;
import io.github.gokborg.components.Bank;

public class AccountCommand implements CommandExecutor
{
	private Map<String, SubCommand> subCommands = new HashMap<>();
	
	public AccountCommand(Bank bank)
	{
		subCommands.put("create", new CreateAccount(bank));
		Balance balance = new Balance(bank);
		subCommands.put("bal", balance);
		subCommands.put("balance", balance);
		subCommands.put("list", new ListAccounts(bank));
		subCommands.put("share", new ShareAccount(bank));
		subCommands.put("unshare", new UnshareAccount(bank));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		//Check if sender is a player, only players need accounts
		if(!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "You must be a player to run this command.");
			return true;
		}
		
		//Check if sub command given
		if(args.length < 1)
		{
			return false;
		}
		
		SubCommand subCmd = subCommands.get(args[0].toLowerCase());
		if(subCmd == null)
		{
			return false;
		}
		
		//Remove first arguement
		int subArgsAmount = args.length - 1;
		String[] subArguments = new String[subArgsAmount];
		System.arraycopy(args, 1, subArguments, 0, subArgsAmount);
		
		subCmd.process(sender, subArguments);
		return true;
	}
}
