package io.github.gokborg.components;

import java.util.ArrayList;
import java.util.List;

public class Account
{
	//May be "null" if its a main account
	private String name;
	
	//List of players which have access to transfer money from this account
	private final List<User> players = new ArrayList<>();
	private boolean shared = false;
	//TODO: Replace 10 with notin
	private int total = 10;
	
	public Account()
	{
	}
	
	public List<User> getAllUsers()
	{
		return players;
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
		players.add(user);
		if(players.size() > 1)
		{
			shared = true;
		}
	}
	
	public void removeUser(User user)
	{
		players.remove(user);
		if(players.size() == 1)
		{
			shared = false;
		}
	}
	
	public boolean hasAccess(User user)
	{
		return players.contains(user);
	}
	
	public String getName()
	{
		return name;
	}
	
	//TODO: Add subcommand to rename a sub-account
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void add(int amount)
	{
		total += amount;
	}
	
	public void remove(int amount)
	{
		total -= amount;
	}
	
	public int getTotal()
	{
		return total;
	}
}
