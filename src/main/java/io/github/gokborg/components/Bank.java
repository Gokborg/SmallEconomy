package io.github.gokborg.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bank
{
	// /pay name:account_name 100
	// /pay name 100
	
	private final int MAX_SUB_ACCOUNTS = 5;
	private Map<String, User> serverBank = new HashMap<>();
	
	public int getSize()
	{
		return serverBank.size();
	}
	
	public void addPlayerAccount(User user)
	{
		serverBank.put(user.getName(), user);
	}
	
	public void removePlayerAccount(User user)
	{
		serverBank.remove(user.getName());
	}
	
	public Account getAccount(User userAccount, String accountName, UUID playerUUID)
	{
		Account requestedAccount = userAccount.getAccount(accountName);
		return requestedAccount == null || !requestedAccount.hasAccess(playerUUID) ? null : requestedAccount;
	}
	
	public User getUser(String name)
	{
		return serverBank.get(name);
	}
}
