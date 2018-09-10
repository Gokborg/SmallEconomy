package io.github.gokborg.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
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
		Balance balance = new Balance(bank);
		subCommands.put("bal", balance);
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
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
	{
		if(args.length == 1)
		{
			List<String> tabCompletionList = new ArrayList<>();
			
			for(String str : new ArrayList<>(subCommands.keySet()))
			{
				if(str.startsWith(args[0]))
				{
					tabCompletionList.add(str);
				}
			}
			return tabCompletionList;
		}
		return Collections.emptyList();
	}
}
