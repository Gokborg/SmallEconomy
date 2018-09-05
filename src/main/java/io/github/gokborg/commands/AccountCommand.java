package io.github.gokborg.commands;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.gokborg.commands.acc.CreateAccount;
import io.github.gokborg.commands.acc.SubCommand;
import io.github.gokborg.components.Bank;

public class AccountCommand implements CommandExecutor{
	
	private Map<String, SubCommand> subCommands = new HashMap<>();
	
	public AccountCommand(Bank bank) {
		subCommands.put("create", new CreateAccount(bank));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!command.getName().equalsIgnoreCase("acc")) {
			return false;
		}
		
		SubCommand subCmd = subCommands.get(args[0].toLowerCase());
		if (subCmd == null) {
			sender.sendMessage(Color.RED + "That is not a valid sub-command!");
			return true;
		}
		
		subCmd.process(sender, command, label, args);
		
		return true;
	}
}
