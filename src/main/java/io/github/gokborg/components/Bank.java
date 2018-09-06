package io.github.gokborg.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//TODO: Prevent creating more than 5 accounts.
public class Bank
{
	//TODO: Have two maps, one by UUID and by Name; for player and argument lookups
	private Map<String, User> serverBank = new HashMap<>();
	
	public int getSize()
	{
		return serverBank.size();
	}
	
	//TODO: Users should be created in the bank
	public void addPlayerAccount(User user)
	{
		serverBank.put(user.getName(), user);
	}
	
	//TODO: remove by UUID?
	public void removePlayerAccount(User user)
	{
		serverBank.remove(user.getName());
	}
	
	//TODO: Get accounts by proper account name: <playername>[:<sub-account-name>]
	public Account getAccount(User userAccount, String accountName, UUID playerUUID)
	{
		Account requestedAccount = userAccount.getAccount(accountName);
		return requestedAccount == null || !requestedAccount.hasAccess(playerUUID) ? null : requestedAccount;
	}
	
	//TODO: Add get by UUID
	public User getUser(String name)
	{
		return serverBank.get(name);
	}
}
