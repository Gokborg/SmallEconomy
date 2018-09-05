package io.github.gokborg.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to run this command.");
			return true;
		}
		
		if (args.length < 1) {
			//Print the usage.
			return false;
		}
		
		SubCommand subCmd = subCommands.get(args[0].toLowerCase());
		if (subCmd == null) {
			sender.sendMessage(ChatColor.RED + "That is not a valid sub-command!");
			return true;
		}
		
		subCmd.process(sender, command, label, args);
		
		return true;
	}
}
