package io.github.gokborg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class PayCommand implements CommandExecutor{
	private Bank bank;
	public PayCommand(Bank bank) {
		this.bank = bank;
	}
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to run this command.");
			return true;
		}
		
		//Get the player's account
		Player player = (Player) sender;
		User playerUser = bank.getUser(player.getName());

		if (playerUser == null) {
			sender.sendMessage(ChatColor.RED + "Please first create an account at the central bank ((/acc create) or nag Gok), to use this command.");
			return true;
		}
		
		Integer transactionAmount;
		//Only allow 2 or 3 arguments
		if (args.length > 1 && args.length < 4)	{
			try {
				//Parse last argument to Integer
				transactionAmount = Integer.parseInt(args[args.length-1]);
				if(transactionAmount < 0)
				{
					sender.sendMessage(ChatColor.RED + "You can only transfer positive amounts.");
					return true;
				}
				else if(transactionAmount == 0)
				{
					sender.sendMessage(ChatColor.GREEN + "Cannot transfer nothing.");
					return true;
				}
			}
			catch(NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Your last argument has to be an integer value.");
				return true;
			}
		}
		else {
			return false;
		}
		
		if (args.length == 3) {
			
			// Splitting "name:account" into {name, account}
			String[] otherPlayerInfo = args[1].split(":");
			
			Account playerAccount = playerUser.getAccount(args[0]);
			User otherUser = bank.getUser(otherPlayerInfo[0]);
			Account otherPlayerAccount = bank.getAccount(otherUser, otherPlayerInfo[1], otherUser.getUUID());
			
			
			//Verifying both accounts
			if (playerAccount == null) {
				player.sendMessage(ChatColor.RED + "The account '" + args[0] + "' does not exist");
				return true;
			}
			else if (playerAccount.getTotal() < transactionAmount) {
				player.sendMessage(ChatColor.RED + "Insufficient funds!");
				return true;
			}
			else if (otherPlayerAccount == null) {
				player.sendMessage(ChatColor.RED + "The account '" + otherPlayerInfo[0] + "' does not exist!");
				return true;
			}
			
			playerAccount.remove(transactionAmount);
			otherPlayerAccount.add(transactionAmount);
			
		}
		else { //Only 2 arguments are left
			//Means they are doing -> /pay <name[:account]> <amount>
			
			// Splitting "name:account" into {name, account}
			String[] otherPlayerInfo = args[0].split(":");
			
			Account playerAccount = playerUser.getAccount(player.getName());
			
			// Check if the player has enough money to pay
			if (playerAccount.getTotal() < transactionAmount) {
				player.sendMessage(ChatColor.RED + "Insufficient funds!");
				return true;
			}
			
			if (otherPlayerInfo.length == 2) {
				// Meaning they typed -> /pay name:account amount
				User otherUser = bank.getUser(otherPlayerInfo[0]);
				
				Account otherPlayerAccount = bank.getAccount(otherUser, otherPlayerInfo[1], otherUser.getUUID());
				
				// Check if the account exists
				if (otherPlayerAccount == null) {
					player.sendMessage(ChatColor.RED + "The account '" + otherPlayerInfo[0] + "' does not exist");
					return true;
				}
				
				//Add to the other player the transaction amount
				otherPlayerAccount.add(transactionAmount);
				
				//Remove from player the transaction Amount
				playerAccount.remove(transactionAmount);
				
				
			}
			else if (otherPlayerInfo.length == 1) {
				// Meaning they typed -> /pay name amount
				
				User otherUser = bank.getUser(args[0]);
				
				if (otherUser == null) {
					player.sendMessage(ChatColor.RED + "The user '" + otherUser.getName() + "' does not exist");
					return true;
				}
				
				//Adding the amount to the other account.
				Account otherPlayerAccount = bank.getAccount(otherUser, otherUser.getName(), otherUser.getUUID());
				
				//Check if account exists
				if (otherPlayerAccount == null) {
					player.sendMessage(ChatColor.RED + "The account '" + otherPlayerAccount.getName() + "' does not exist");
					return true;
				}
				
				//Removing the amount from the player.
				playerAccount.remove(transactionAmount);
				
				//Adding the amount to the other account
				otherPlayerAccount.add(transactionAmount);
				
				//Send a message to the player indicating that the transaction has been complete.
				
			}
		}
		player.sendMessage(ChatColor.GREEN + "Payment complete!");
		return true;
	}
	
}
