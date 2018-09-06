package io.github.gokborg.components;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account
{
	//May be "null" if its a main account
	private String name;
	
	//List of players which have access to transfer money from this account
	//TODO: Store users instead, since each one should have its own money, or at least account.
	private List<UUID> players = new ArrayList<>();
	
	private int total;
	
	//TODO: Add empty constructor for main account
	public Account(String name)
	{
		this.name = name;
	}
	
	//TODO: Replace with User
	public boolean hasAccess(UUID uuid)
	{
		return players.contains(uuid);
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
