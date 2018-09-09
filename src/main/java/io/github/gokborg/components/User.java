package io.github.gokborg.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.github.gokborg.exceptions.CannotCreateAccountException;

public class User
{
	//The key refers to the sub-account-name: <player>:<sub-account-name>
	private final Map<String, Account> subAccounts = new HashMap<>();
	private final List<String> sharedAccounts = new ArrayList<>();
	
	//Main account linked to the User
	private final Account mainAccount = new Account();;
	
	//The last seen name on the server
	private final String name;
	private final UUID playerUUID;
	
	public User(String playerName, UUID playerUUID)
	{
		this.name = playerName;
		this.playerUUID = playerUUID;
		
		//Allow this used to access his own account
		mainAccount.addUser(this);
	}
	
	public Collection<Account> getAllAccounts()
	{
		return subAccounts.values();
	}
	
	public UUID getUUID()
	{
		return playerUUID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public List<String> getSharedAccounts()
	{
		return sharedAccounts;
	}
	
	public void addSharedAccount(String accountName)
	{
		sharedAccounts.add(accountName);
	}
	
	public void removeSharedAccount(String accountName)
	{
		sharedAccounts.remove(accountName);
	}
	
	public Account getAccount(String name)
	{
		return subAccounts.get(name.toLowerCase());
	}
	
	public Account getMainAccount()
	{
		return mainAccount;
	}
	
	public void createAccount(String accountName) throws CannotCreateAccountException
	{
		if(subAccounts.size() > 4)
		{
			throw new CannotCreateAccountException("You may only have 5 sub accounts.");
		}
		
		if(subAccounts.containsKey(accountName.toLowerCase()))
		{
			throw new CannotCreateAccountException("You already have an account with name '" + accountName + "'.");
		}
		
		Account newAccount = new Account(accountName);
		newAccount.addUser(this);
		subAccounts.put(accountName.toLowerCase(), newAccount);
	}
	
	public void removeAccount(String nameOfAccount)
	{
		subAccounts.remove(nameOfAccount.toLowerCase());
	}
	
	public void removeAccount(Account account)
	{
		subAccounts.remove(account.getName().toLowerCase(), account);
	}
}
