package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class CreateAccount extends SubCommand
{
	private Bank bank;
	
	public CreateAccount(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void process(CommandSender sender, String[] args)
	{
		/*
		 * args[0] = create
		 * args[1] = account_name
		 */
		if(args.length > 2)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /acc create [name]");
			return;
		}
		
		Player player = (Player) sender;
		User playerUser = bank.getUser(player.getName());
		
		//Create a User if the sender doesn't have one.	
		if(playerUser == null)
		{
			playerUser = new User(player.getName(), player.getUniqueId());
			bank.addPlayerAccount(playerUser);
		}
		
		if(args.length == 1)
		{
			//Check if they already have a main account!
			if(playerUser.getAccount(playerUser.getName()) != null)
			{
				player.sendMessage(ChatColor.RED + "You already have an account!");
				return;
			}
			
			//Creates an account under that user using their name.
			playerUser.addAccount(new Account(playerUser.getName()));
		}
		else
		{ //Only two args possible here
			// args[2] has the account name
			
			//Create an account with args[2] as name.
			playerUser.addAccount(new Account(args[2]));
		}
	}
}
