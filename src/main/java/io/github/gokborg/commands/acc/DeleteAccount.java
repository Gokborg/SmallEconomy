package io.github.gokborg.commands.acc;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CommandException;

public class DeleteAccount extends SubCommand
{
	private final Bank bank;
	
	public DeleteAccount(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void execute(Player player, String[] args) throws CommandException
	{
		User user = getUser(bank, player);
		
		check(args.length != 1, "Usage: /acc del <account_name>");
		
		Account targetAccount = getAccount(user, args[0]);
		
		user.removeAccount(targetAccount);
		
		player.sendMessage(ChatColor.GREEN + "Successfully removed account.");
	}
	
	@Override
	public List<String> tabComplete(Player player, String[] args)
	{
		return Collections.emptyList();
	}
}
