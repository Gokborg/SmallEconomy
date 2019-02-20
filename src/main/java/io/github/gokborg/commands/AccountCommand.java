package io.github.gokborg.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.acc.Balance;
import io.github.gokborg.commands.acc.CreateAccount;
import io.github.gokborg.commands.acc.ListAccounts;
import io.github.gokborg.commands.acc.ShareAccount;
import io.github.gokborg.commands.acc.UnshareAccount;
import io.github.gokborg.components.Bank;
import io.github.gokborg.exceptions.CommandException;

public class AccountCommand extends CommandWrapper
{
	private Map<String, SubCommand> subCommands = new HashMap<>();
	
	public AccountCommand(Bank bank)
	{
		subCommands.put("create", new CreateAccount(bank));
		subCommands.put("bal", new Balance(bank));
		subCommands.put("list", new ListAccounts(bank));
		subCommands.put("share", new ShareAccount(bank));
		subCommands.put("unshare", new UnshareAccount(bank));
	}
	
	@Override
	public boolean execute(CommandSender sender, String[] args) throws CommandException
	{
		//Check if sender is a player, only players need accounts
		Player player = getPlayer(sender);
		
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
		
		subCmd.execute(player, subArguments);
		
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args)
	{
		String lowerCase = args[0].toLowerCase();
		if(args.length == 1)
		{
			return subCommands.keySet().stream().filter(sc -> sc.startsWith(lowerCase)).collect(Collectors.toList());
		}
		
		SubCommand subCommand = subCommands.get(lowerCase);
		if(subCommand != null)
		{
			int subArgsAmount = args.length - 1;
			String[] subArguments = new String[subArgsAmount];
			System.arraycopy(args, 1, subArguments, 0, subArgsAmount);
			
			return subCommand.tabComplete(player, subArguments);
		}
		
		
		return Collections.emptyList();
	}
}
