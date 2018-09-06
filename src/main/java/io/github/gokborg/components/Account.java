package io.github.gokborg.components;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account
{
	private String name;
	
	//List of which uuid's have access to this account
	private List<UUID> players = new ArrayList<>();
	
	private int total = 0;
	
	public Account(String name)
	{
		this.name = name;
	}
	
	public boolean hasAccess(UUID uuid)
	{
		return players.contains(uuid);
	}
	
	public String getName()
	{
		return name;
	}
	
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
	
	public void setTotal(int total)
	{
		this.total = total;
	}
}
