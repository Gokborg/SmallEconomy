package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class UnshareAccount extends SubCommand
{
	private final Bank bank;
	
	public UnshareAccount(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void process(CommandSender sender, String[] args)
	{
		User player = bank.getUser(((Player) sender).getUniqueId());
		
		if(player == null)
		{
			sender.sendMessage(ChatColor.RED + "Please first create an account '/acc create', to use this command.");
			return;
		}
		if(args.length != 2)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /acc unshare <account_name> <user>");
			return;
		}
		
		Account playerAccount = player.getAccount(args[0]);
		
		if(playerAccount == null)
		{
			sender.sendMessage(ChatColor.RED + "The account '" + args[0] + "' does not exist.");
		}
		if(!playerAccount.isShared())
		{
			sender.sendMessage(ChatColor.RED + "The account '" + args[0] + "' isn't shared");
			return;
		}
		
		User otherUser = bank.getUser(args[1]);
		
		if(otherUser == null)
		{
			sender.sendMessage(ChatColor.RED + "The user '" + args[1] + "' does not exist.");
		}
		
		playerAccount.removeUser(otherUser);
		
		sender.sendMessage(ChatColor.GREEN + "Removed '" + args[1] + "' from account '" + args[0] + "'");
		
	}
}
