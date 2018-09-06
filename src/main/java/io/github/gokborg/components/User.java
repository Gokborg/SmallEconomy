package io.github.gokborg.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
	
	//TODO: As long as accounts don't swap owners, they should not be created outside this object
	public void addAccount(Account account)
	{
		subAccounts.put(account.getName().toLowerCase(), account);
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
