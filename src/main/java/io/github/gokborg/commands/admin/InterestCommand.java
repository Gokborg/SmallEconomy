package io.github.gokborg.commands.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Bank;
import io.github.gokborg.exceptions.CommandException;

public class InterestCommand extends SubCommand
{
	private Map<String, SubCommand> subCommands = new HashMap<>();
	
	public InterestCommand(Bank bank)
	{
		subCommands.put("set", new SetInterest(bank));
	}

	public void execute(Player player, String[] args) throws CommandException
	{
		check(args.length < 1, "Usage: /mngacc interest [set]");
		SubCommand subCmd = subCommands.get(args[0].toLowerCase());
		if(subCmd == null)
		{
			return;
		}
		//Remove first arguement
		int subArgsAmount = args.length - 1;
		String[] subArguments = new String[subArgsAmount];
		System.arraycopy(args, 1, subArguments, 0, subArgsAmount);
		
		subCmd.execute(player, subArguments);
	}

	@Override
	public List<String> tabComplete(Player player, String[] args)
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
