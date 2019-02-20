package io.github.gokborg.commands.acc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		
		check(args.length != 1, "Usage: /acc unshare <account_name> [user]");
		
		Account playerAccount = getAccount(user, args[0]);
		check(!playerAccount.isShared(), "The account '" + args[0] + "' isn't shared");
		
		if(args.length == 2)
		{
			User otherUser = bank.getUser(args[1]);
			check(otherUser, "The user '" + args[1] + "' does not exist.");
			
			playerAccount.removeUser(otherUser);
			player.sendMessage(ChatColor.GREEN + "Removed '" + args[1] + "' from account '" + args[0] + "'");
			
			return;
		}
		
		player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Removed:"));
		for(User sharedUser : playerAccount.getAllSharedUsers())
		{
			player.sendMessage(ChatColor.GREEN + "-> " + sharedUser.getName());
		}
		playerAccount.clearAllSharedUsers();
	}
	
	@Override
	public List<String> tabComplete(Player player, String[] args)
	{
		if(args.length == 1)
		{
			User user = bank.getUser(player.getUniqueId());
			return user != null && user.hasSharedAccounts() ? user.getSharedAccounts() : Collections.emptyList();
		}
		
		if(args.length == 2)
		{
			List<String> allSharedUsers = new ArrayList<>();
			
			for(User user : bank.getUser(player.getUniqueId()).getAccount(args[0]).getAllSharedUsers())
			{
				allSharedUsers.add(user.getName());
			}
			
			return allSharedUsers;
		}
		
		return Collections.emptyList();
	}
}
