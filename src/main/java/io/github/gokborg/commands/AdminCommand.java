package io.github.gokborg.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.admin.GiveMoney;
import io.github.gokborg.components.Bank;
import io.github.gokborg.exceptions.CommandException;

public class AdminCommand extends CommandWrapper
{
	private Map<String, SubCommand> subCommands = new HashMap<>();
	private final TabCompleteTools tabCompleteTools;
	public AdminCommand(Bank bank)
	{
		this.tabCompleteTools = new TabCompleteTools(bank);
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
		if(args.length == 1)
		{
			List<String> tabCompleteList = new ArrayList<>();
			
			for(String subCmd : new ArrayList<>(subCommands.keySet()))
			{
				if(subCmd.startsWith(args[0]))
				{
					tabCompleteList.add(subCmd);
				}
			}
			return tabCompleteList;
		}
		else if(args.length == 2)
		{
			if(args[0].equalsIgnoreCase("give"))
			{
				return tabCompleteTools.closestUserWithAccount(args[1]);
			}
		}
		else if(args.length == 3)
		{
			if(args[0].equalsIgnoreCase("give"))
			{
				return Arrays.asList("+", "-", "=");
			}
		}
		
		return Collections.emptyList();
	}
}
