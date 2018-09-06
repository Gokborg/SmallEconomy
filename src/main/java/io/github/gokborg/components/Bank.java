package io.github.gokborg.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//TODO: Prevent creating more than 5 accounts.
public class Bank
{
	private Map<String, User> userByName = new HashMap<>();
	private Map<UUID, User> userByUUID = new HashMap<>();
	
	public int getSize()
	{
		return userByUUID.size();
	}
	
	//TODO: Users should be created in the bank
	public void addPlayerAccount(User user)
	{
		userByName.put(user.getName(), user);
		userByUUID.put(user.getUUID(), user);
	}
	
	public boolean removePlayerAccount(UUID uuid)
	{
		User removedUser = userByUUID.remove(uuid);
		if(removedUser == null)
		{
			return false;
		}
		
		userByName.remove(removedUser.getName());
		return true;
	}
	
	//TODO: Get accounts by proper account name: <playername>[:<sub-account-name>]
	public Account getAccount(User userAccount, String accountName, UUID playerUUID)
	{
		Account requestedAccount = userAccount.getAccount(accountName);
		return requestedAccount == null || !requestedAccount.hasAccess(playerUUID) ? null : requestedAccount;
	}
	
	public User getUser(UUID uuid)
	{
		return userByUUID.get(uuid);
	}
	
	public User getUser(String name)
	{
		return userByName.get(name);
	}
}
