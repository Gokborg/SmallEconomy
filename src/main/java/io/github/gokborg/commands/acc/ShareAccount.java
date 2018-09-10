package io.github.gokborg.commands.acc;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.commands.TabCompleteTools;
import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CommandException;

public class ShareAccount extends SubCommand
{
	private final Bank bank;
	
	public ShareAccount(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void execute(Player player, String[] args) throws CommandException
	{
		User playerUser = getUser(bank, player);
		
		check(args.length != 2, "Usage: /acc share <account> <user>");
		
		//If they attempt to share w/ themselves stop them.
		check(args[1].equalsIgnoreCase(player.getName()), "You can not share an account with yourself.");
		
		//Shares with the other user
		User otherUser = bank.getUser(args[1]);
		
		//Incase the otheruser does not exist
		check(otherUser, "User '" + args[1] + "' does not exist.");
		
		Account account = getAccount(playerUser, args[0]);
		
		account.addUser(otherUser);
		otherUser.addSharedAccount(account.getName());
		player.sendMessage(ChatColor.GREEN + "Successfully shared '" + args[0] + "' with " + args[1]);
	}
	
	@Override
	public List<String> tabComplete(Player player, String[] args)
	{
		if(args.length == 1)
		{
			return TabCompleteTools.closestAccount(bank.getUser(player.getName()), args[0]);
		}
		
		if(args.length == 2)
		{
			return TabCompleteTools.closestUser(bank, args[1]);
		}
		
		return Collections.emptyList();
	}
}
