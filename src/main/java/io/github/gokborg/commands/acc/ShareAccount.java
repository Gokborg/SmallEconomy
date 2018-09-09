package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CommandException;

public class ShareAccount extends SubCommand
{
	private Bank bank;
	
	public ShareAccount(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException
	{
		User playerUser = getUser(bank, getPlayer(sender));
		
		check(args.length != 2, "Usage: /acc share <account> <user>");
		
		//If they attempt to share w/ themselves stop them.
		check(args[1].equalsIgnoreCase(sender.getName()), "You can not share an account with yourself.");
		
		
		//Shares with the other user
		User otherUser = bank.getUser(args[1]);
		
		//Incase the otheruser does not exist
		check(otherUser, "User '" + args[1] + "' does not exist.");
		
		Account account = getAccount(playerUser, args[0]); 
		
		account.addUser(otherUser);
		otherUser.addSharedAccount(account.getName());
		sender.sendMessage(ChatColor.GREEN + "Successfully shared '" + args[0] + "' with " + args[1]);
	
	}
}
