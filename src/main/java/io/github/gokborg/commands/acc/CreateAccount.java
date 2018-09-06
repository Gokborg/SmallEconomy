package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

//TODO: Do noth create the main account as sub-account with playername
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
		if(args.length > 1)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /acc create [name]");
			return;
		}
		
		Player player = (Player) sender;
		User playerUser = bank.getUser(player.getUniqueId());
		
		//Create a User if the sender doesn't have one.	
		if(playerUser == null)
		{
			playerUser = new User(player.getName(), player.getUniqueId());
			bank.addPlayerAccount(playerUser);
		}
		
		if(args.length == 0)
		{
			//Check if they already have a main account!
			//TODO: Obsolte, each User should always have a main account.
			if(playerUser.getAccount(playerUser.getName()) != null)
			{
				player.sendMessage(ChatColor.RED + "You already have an account!");
				return;
			}
			
			//Creates an account under that user using their name.
			playerUser.addAccount(new Account(playerUser.getName()));
		}
		else //Only one args possible here
		{
			//Create an account with args[0] as name.
			playerUser.addAccount(new Account(args[0]));
		}
	}
}
