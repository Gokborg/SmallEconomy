package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CommandException;

public class DeleteAccount extends SubCommand
{
	private Bank bank;
	
	public DeleteAccount(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException
	{
		User player = getUser(bank, getPlayer(sender));
		
		check(args.length != 1, "Usage: /acc del <account_name>");
		
		Account targetAccount = getAccount(player, args[0]);
		
		player.removeAccount(targetAccount);
		
		sender.sendMessage(ChatColor.GREEN + "Successfully removed account.");
	}
}
