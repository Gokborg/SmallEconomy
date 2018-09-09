package io.github.gokborg.commands;

import java.util.HashMap;
import java.util.Map;

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
}
