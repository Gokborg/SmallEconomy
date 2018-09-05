package io.github.gokborg.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class User{
	//Don't let players create accounts with their own name.
	private Map<String, Account> subAccounts = new HashMap<>();
	
	private final String name;
	private final UUID playerUUID;
	
	public User(String playerName, UUID playerUUID) {
		this.name = playerName;
		this.playerUUID = playerUUID;
		subAccounts.put(name, new Account(name));
	}
	
	public UUID getUUID() {
		return playerUUID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return subAccounts.size();
	}
	
	public Account getAccount(String name) {
		return subAccounts.get(name);
	}
	
	public void addAccount(Account account) {
		 
		 subAccounts.put(account.getName(), account);
	}
	
	//Based on name
	public void removeAccount(String nameOfAccount) {
		subAccounts.remove(nameOfAccount);
	}
	
	//Based on object
	public void removeAccount(Account account) {
		subAccounts.remove(account.getName(), account);
	}
}
