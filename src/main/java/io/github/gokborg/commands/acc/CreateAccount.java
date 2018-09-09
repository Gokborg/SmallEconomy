package io.github.gokborg.commands.acc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.commands.SubCommand;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CannotCreateAccountException;
import io.github.gokborg.exceptions.CommandException;

public class CreateAccount extends SubCommand
{
	private final Bank bank;
	
	public CreateAccount(Bank bank)
	{
		this.bank = bank;
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException
	{
		if(args.length > 1)
		{
			sender.sendMessage(ChatColor.RED + "Usage: /acc create [name]");
			return;
		}
		
		Player player = getPlayer(sender);
		
		User playerUser = getUser(bank, player);
		
		//Create a User for the executing player if he has none.
		if(playerUser == null)
		{
			playerUser = bank.createUser(player.getName(), player.getUniqueId());
			sender.sendMessage(ChatColor.GREEN + "Created personal account.");
		}
		else if(args.length == 0)
		{
			sender.sendMessage(ChatColor.RED + "You already have a personal account.");
		}
		
		if(args.length == 1)
		{
			try
			{
				//Create an account with args[0] as name.
				playerUser.createAccount(args[0]);
				sender.sendMessage(ChatColor.GREEN + "Created account '" + args[0] + "'.");
			}
			catch(CannotCreateAccountException e)
			{
				sender.sendMessage(ChatColor.RED + e.getMessage());
			}
		}
	}
}
