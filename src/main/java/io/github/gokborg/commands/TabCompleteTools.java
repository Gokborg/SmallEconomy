package io.github.gokborg.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class TabCompleteTools
{
	public static List<String> closestUserWithAccount(Bank bank, String str)
	{
		List<String> tabCompleteList = new ArrayList<>();
		
		int colonIndex = str.indexOf(':');
		
		for(String userStr : bank.getAllUsernames())
		{
			if(colonIndex != -1)
			{
				User user = bank.getUser(str.substring(0, colonIndex));
				if(user != null)
				{
					for(String accStr : user.getAllAccountsName())
					{
						if(accStr.startsWith(str.substring(colonIndex + 1, str.length())))
						{
							tabCompleteList.add(user.getName().toLowerCase() + ":" + accStr);
						}
					}
					
					return tabCompleteList;
				}
			}
			else if(!str.isEmpty() && userStr.startsWith(str))
			{
				
				tabCompleteList.add(userStr);
				return tabCompleteList;
			}
		}
		
		return bank.getAllUsernames();
	}
	
	//Looks for users and looks for users:account
	public static List<String> closestUser(Bank bank, String str)
	{
		List<String> tabCompleteList = new ArrayList<>();
		
		for(String userStr : bank.getAllUsernames())
		{
			if(!str.isEmpty() && userStr.startsWith(str))
			{
				tabCompleteList.add(userStr);
				return tabCompleteList;
			}
		}
		
		return bank.getAllUsernames();
	}
	
	
	
	//Looks for just account
	public static List<String> closestAccount(User user, String str)
	{
		List<String> tabCompleteList = new ArrayList<>();
		
		if(user != null)
		{
			for(String accStr : user.getAllAccountsName())
			{
				if(accStr.startsWith(str))
				{
					tabCompleteList.add(accStr);
				}
			}
			
			return tabCompleteList != null ? tabCompleteList : user.getAllAccountsName();
		}
		
		return Collections.emptyList();
	}
}
