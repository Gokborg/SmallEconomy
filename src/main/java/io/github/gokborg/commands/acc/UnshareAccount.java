package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CommandException;

public class UnshareAccount extends SubCommand
{
	private final Bank bank;
	
	public UnshareAccount(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException
	{
		User player = getUser(bank, getPlayer(sender));
		
		check(args.length != 2, "Usage: /acc unshare <account_name> <user>");
		
		Account playerAccount = getAccount(player, args[0]);
		check(!playerAccount.isShared(), "The account '" + args[0] + "' isn't shared");
		
		User otherUser = bank.getUser(args[1]);
		check(otherUser, "The user '" + args[1] + "' does not exist.");
		
		playerAccount.removeUser(otherUser);
		
		sender.sendMessage(ChatColor.GREEN + "Removed '" + args[1] + "' from account '" + args[0] + "'");
		
	}
}
