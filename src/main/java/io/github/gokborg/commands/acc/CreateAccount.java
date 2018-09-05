package io.github.gokborg.commands.acc;

import java.awt.Color;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class CreateAccount extends SubCommand{
	private Bank bank;
	public CreateAccount(Bank bank) {
		this.bank = bank;
	}
	
	
	@Override
	public void process(CommandSender sender, Command command, String label, String[] args) {
		/*
		 * args[0] = create
		 * args[1] = account_name
		 */
		Player player = (Player) sender;
		User playerUser = bank.getUser(player.getName());
		
		if (args.length >= 3) {
			player.sendMessage(Color.RED + "Insufficient arguments");
			return;
		}
		else if (args.length == 1) {
			//Create an account with default MC name. First check if account already exists else create it
			
			//Create a user if they don't have one.	
			if (playerUser == null) {
				playerUser = new User(player.getName(), player.getUniqueId());
				bank.addPlayerAccount(playerUser);
			}
			
			//Check if they already have a main account!
			if (playerUser.getAccount(playerUser.getName()) != null) {
				player.sendMessage(Color.RED + "The account already exists!");
				return;
			}
			
			//Creates an account under that user using their name.
			playerUser.addAccount(new Account(playerUser.getName()));
			
		}
		else if (args.length == 2) {
			// args[2] has the account name
			
			//Create an account with args[2] as name.
			playerUser.addAccount(new Account(args[2]));
		}
		
	}
	
}
