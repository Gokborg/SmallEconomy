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

		if (args.length == 3 && isNum(args[2])) {
			//Means they are doing -> /pay [account] <name[:account]> <amount>
			
			// Splitting "name:account" into {name, account}
			String[] otherPlayerInfo = args[1].split(":");
			
			int transactionAmount = Integer.parseInt(args[2]);
			
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
		else if (args.length == 2  && isNum(args[1])){
			//Means they are doing -> /pay <name[:account]> <amount>
			
			// Splitting "name:account" into {name, account}
			String[] otherPlayerInfo = args[0].split(":");
			
			//Getting the payment amount
			int transactionAmount = Integer.parseInt(args[1]);
			
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
		else {
			return false;
		}
		player.sendMessage(ChatColor.GREEN + "Payment complete!");
		return true;
	}
	
	private boolean isNum(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	
}
