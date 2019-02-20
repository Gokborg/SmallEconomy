package io.github.gokborg.file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class Writer
{
	public static void write(String filePath, Bank bank) throws IOException
	{
		PrintWriter writer = new PrintWriter(new FileWriter(filePath));
		
		
		writer.println(bank.getInterestTime());
		writer.println(bank.getInterestRate());
		
		//1. Get all the users
		List<User> users = bank.getAllUsers();
		//2. Get all the subaccounts + mainaccount
		for(User user : users)
		{
			writer.println("user:");
			writer.println(user.getName());
			writer.println(user.getUUID());
			writer.println(user.getMainAccount().getTotal());
		}
		
		for(User user : users)
		{
			for(Account acc : user.getAllAccounts())
			{
				writer.println("acc:");
				writer.println(user.getUUID());
				writer.println(acc.getName());
				writer.println(acc.getTotal());
				if(acc.getAllSharedUsers().size() <= 1)
				{
					writer.println("none");
				}
				else
				{
					String sharedUsers = "";
					for(User sharedUser : acc.getAllSharedUsers())
					{
						if(!sharedUser.getName().equals(user.getName()))
						{
							sharedUsers += sharedUser.getName() + " ";
						}
					}
					writer.println(sharedUsers);
				}
			}
		}
		writer.close();
	}
}
