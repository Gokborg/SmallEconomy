package io.github.gokborg.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.github.gokborg.exceptions.AccountNotFoundException;

public class Bank
{
	private Map<String, User> userByName = new HashMap<>();
	private Map<UUID, User> userByUUID = new HashMap<>();
	
	public List<String> getAllUsers()
	{
		return new ArrayList<>(userByName.keySet());
	}
	
	public User createUser(String username, UUID uuid)
	{
		User user = new User(username, uuid);
		userByName.put(user.getName().toLowerCase(), user);
		userByUUID.put(user.getUUID(), user);
		return user;
	}
	
	//Returns if the account has been removed.
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
	
	public Account parseAccountID(String accountID) throws AccountNotFoundException
	{
		int doublePointIndex = accountID.indexOf(':');
		
		if(doublePointIndex == -1) //Format: <username>
		{
			User accountOwner = userByName.get(accountID.toLowerCase());
			if(accountOwner == null)
			{
				throw new AccountNotFoundException("User '" + accountID + "' has no accounts.");
			}
			
			return accountOwner.getMainAccount();
		}
		else //Format: <username>:<account-name>
		{
			String userName = accountID.substring(0, doublePointIndex);
			String accountName = accountID.substring(doublePointIndex + 1);
			
			User accountOwner = userByName.get(userName.toLowerCase());
			if(accountOwner == null)
			{
				throw new AccountNotFoundException("User '" + userName + "' has no account(s).");
			}
			
			Account account = accountOwner.getAccount(accountName);
			if(account == null)
			{
				throw new AccountNotFoundException("User '" + userName + "' has no account '" + accountName + "'.");
			}
			
			return account;
		}
	}
	
	public User getUser(UUID uuid)
	{
		return userByUUID.get(uuid);
	}
	
	public User getUser(String name)
	{
		return userByName.get(name.toLowerCase());
	}
}
