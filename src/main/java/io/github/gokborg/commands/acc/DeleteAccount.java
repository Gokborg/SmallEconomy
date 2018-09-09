package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class DeleteAccount extends SubCommand
{
	private Bank bank;
	
	public DeleteAccount(Bank bank)
	{
		this.bank = bank;
	}
	//text
	@Override
	public void process(CommandSender sender, String[] args)
	{
		User player = bank.getUser(((Player) sender).getUniqueId());
		if(player == null)
		{
			sender.sendMessage(ChatColor.RED + "Please first create an account '/acc create', to use this command.");
			return;
		}
		
		if(args.length != 1)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /acc del <account_name>");
			return;
		}
		
		Account targetAccount = player.getAccount(args[0]);
		if(targetAccount == null)
		{
			sender.sendMessage(ChatColor.RED + "The account you are trying to delete does not exist.");
			return;
		}
		player.removeAccount(targetAccount);
		sender.sendMessage(ChatColor.GREEN + "Successfully removed account.");
	}
}
