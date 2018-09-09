package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
	public void execute(Player player, String[] args) throws CommandException
	{
		User user = getUser(bank, player);
		
		check(args.length != 2, "Usage: /acc unshare <account_name> <user>");
		
		Account playerAccount = getAccount(user, args[0]);
		check(!playerAccount.isShared(), "The account '" + args[0] + "' isn't shared");
		
		User otherUser = bank.getUser(args[1]);
		check(otherUser, "The user '" + args[1] + "' does not exist.");
		
		playerAccount.removeUser(otherUser);
		
		player.sendMessage(ChatColor.GREEN + "Removed '" + args[1] + "' from account '" + args[0] + "'");
	}
}
