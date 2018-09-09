package io.github.gokborg.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.github.gokborg.exceptions.AccountNotFoundException;

public class Bank
{
	private Map<String, User> userByName = new HashMap<>();
	private Map<UUID, User> userByUUID = new HashMap<>();
	
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
	
	public Account parseAccountID(String accountID, User owner) throws AccountNotFoundException
	{
		
		int doublePointIndex = accountID.indexOf(':');
		
		if(doublePointIndex == -1) //Format: <username>
		{
			if (owner != null)
			{
				if (accountID.equalsIgnoreCase(owner.getName()))
				{
					return owner.getMainAccount();
				}
				Account account = owner.getAccount(accountID);
				if (account == null)
				{
					throw new AccountNotFoundException("Account '" + accountID + "' does not exist");
				}
				return account;
			}
			else 
			{
				User accountOwner = userByName.get(accountID.toLowerCase());
				if(accountOwner == null)
				{
					throw new AccountNotFoundException("User '" + accountID + "' does not exist");
				}
				
				return accountOwner.getMainAccount();
			}
		}
		else //Format: <username>:<account-name>
		{
			String userName = accountID.substring(0, doublePointIndex);
			String accountName = accountID.substring(doublePointIndex+1);
			
			User accountOwner = userByName.get(userName.toLowerCase());
			if(accountOwner == null)
			{
				throw new AccountNotFoundException("User '" + userName + "' has no accounts.");
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
		return userByName.get(name);
	}
}
