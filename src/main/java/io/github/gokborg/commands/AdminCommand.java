package io.github.gokborg.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.admin.GiveMoney;
import io.github.gokborg.components.Bank;
import io.github.gokborg.exceptions.CommandException;

public class AdminCommand extends CommandWrapper
{
	private Map<String, SubCommand> subCommands = new HashMap<>();
	
	public AdminCommand(Bank bank)
	{
		subCommands.put("give", new GiveMoney(bank));
	}
	
	@Override
	protected boolean execute(CommandSender sender, String[] args) throws CommandException
	{
		Player player = getPlayer(sender);
		
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
		if(sender instanceof Player)
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
				
				return subCommand.tabComplete((Player) sender, subArguments);
			}
		}
		
		return Collections.emptyList();
	}
}
