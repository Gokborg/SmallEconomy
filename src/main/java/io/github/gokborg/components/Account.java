package io.github.gokborg.components;

import java.util.ArrayList;
import java.util.List;

public class Account
{
	//May be "null" if its a main account
	private String name;
	
	//TODO: read discord pm ecconia
	//List of players which have access to transfer money from this account
	private final List<User> sharedUsers = new ArrayList<>();
	private boolean shared = false;
	//TODO: Replace 10 with notin
	private long total = 0;
	
	public Account()
	{
	}
	
	public void setShared()
	{
		shared = true;
	}
	
	public void clearAllSharedUsers()
	{
		sharedUsers.clear();
	}
	
	public List<User> getAllSharedUsers()
	{
		return sharedUsers;
	}
	
	public Account(String name)
	{
		this.name = name;
	}
	
	public boolean isShared()
	{
		return shared;
	}
	
	public void addUser(User user)
	{
		sharedUsers.add(user);
		if(sharedUsers.size() > 1)
		{
			shared = true;
		}
	}
	
	public void removeUser(User user)
	{
		sharedUsers.remove(user);
		if(sharedUsers.size() == 1)
		{
			shared = false;
		}
	}
	
	public User getOwner()
	{
		return sharedUsers.get(0);
	}
	
	public boolean hasOwnership(User user)
	{
		return sharedUsers.get(0).equals(user);
	}
	
	public boolean hasAccess(User user)
	{
		return sharedUsers.contains(user);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void add(long amount)
	{
		total += amount;
	}
	public void remove(long amount)
	{
		total -= amount;
	}
	
	public void setTotal(long total)
	{
		this.total = total;
	}
	
	public long getTotal()
	{
		return total;
	}
}
