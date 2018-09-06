package io.github.gokborg.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.github.gokborg.exceptions.CannotCreateAccountException;

//TODO: Save main account in own field
public class User
{
	//The key refers to the sub-account-name: <player>:<sub-account-name>
	private Map<String, Account> subAccounts = new HashMap<>();
	
	//The last seen name on the server
	private final String name;
	private final UUID playerUUID;
	
	public User(String playerName, UUID playerUUID)
	{
		this.name = playerName;
		this.playerUUID = playerUUID;
		subAccounts.put(name.toLowerCase(), new Account(name));
	}
	
	public UUID getUUID()
	{
		return playerUUID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getSize()
	{
		return subAccounts.size();
	}
	
	public Account getAccount(String name)
	{
		return subAccounts.get(name.toLowerCase());
	}
	
	public Account getMainAccount()
	{
		//TODO: Change to main account field:
		return subAccounts.get(name.toLowerCase());
	}
	
	public void createAccount(String accountName) throws CannotCreateAccountException
	{
		if(subAccounts.size() > 5)
		{
			throw new CannotCreateAccountException("You may only have 5 sub accounts.");
		}
		
		if(subAccounts.containsKey(accountName.toLowerCase()))
		{
			throw new CannotCreateAccountException("You already have an account with name '" + accountName + "'.");
		}
		
		Account account = new Account(accountName);
		subAccounts.put(accountName.toLowerCase(), account);
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
