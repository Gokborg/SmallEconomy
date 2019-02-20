package io.github.gokborg.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;
import io.github.gokborg.exceptions.CannotCreateAccountException;

public class Reader
{
	public static Bank read(String filePath) throws IOException, CannotCreateAccountException
	{
		Bank bank = new Bank(); 
		
		File file = new File(filePath);
		
		file.createNewFile();
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		LineBuffer lineBuffer = new LineBuffer(br.lines().toArray(String[]::new));
		
		String line = lineBuffer.next();
		if(line == null)
		{
			br.close();
			return new Bank();
		}
		bank.setInterestTime(Long.parseLong(line));
		
		bank.setInterestRate(Double.parseDouble(lineBuffer.next()));
		line = lineBuffer.next();
		while(line != null)
		{
			//Users written first to file so that account setup is easy peezy
			// *A list of users are stored on accounts that are shared* (Weird but ok)
			if("user:".equals(line))
			{
				/*
				 * user:
				 * Gokborg
				 * UUID OF GOKBORG
				 * MAIN ACCOUNT BAL OF GOKBORG
				 * user:
				 * Ecconia
				 * UUID OF ECCONIA
				 * MAIN ACCOUNT BAL OF ECCONIA
				 */
				String usersName = lineBuffer.next();
				String usersUUID = lineBuffer.next();
				String usersMainAccountBal = lineBuffer.next();
				User user = bank.createUser(usersName, UUID.fromString(usersUUID));
				user.getMainAccount().setTotal(Long.parseLong(usersMainAccountBal));
				//System.out.println("User<" + usersName + ", " + usersUUID + ", " + usersMainAccountBal +">");
			}
			else if("acc:".equals(line))
			{
				/*
				 * acc:
				 * UUID OF GOKBORG
				 * NAME OF SUBACCOUNT
				 * BALANCE OF SUBACCOUNT
				 * SHARED USERS ("none" if no shared users)
				 * acc:
				 * UUID OF ECCONIA
				 * NAME OF SUBACCOUNT
				 * BALANCE OF SUBACCOUNT
				 * SHARED USERS ("none" if no shared users)
				 */
				
				//Gets the owner's user object
				User user = bank.getUser(UUID.fromString(lineBuffer.next()));
				String subAccountName = lineBuffer.next();
				user.createAccount(subAccountName);
				//Gets the owner's sub account
				Account userSubAccount = user.getAccount(subAccountName);
				//Attaches the subaccount's balance from file to the subaccount of owner
				userSubAccount.setTotal(Long.parseLong(lineBuffer.next()));
				
				String potentialShared = lineBuffer.next();
				
				//Adds the rest of the plebians to the subaccount (If there are any plebians)
				if(!"none".equals(potentialShared))
				{
					userSubAccount.setShared();
					String[] allUsersAttached = potentialShared.split(" ");
					for(String username : allUsersAttached)
					{
						User subUser = bank.getUser(username);
						if(subUser == null)
						{
							//TODO: Maybe send error someone else -> get this checked
							System.out.println("Failed to find user \"" + username + "\"! Ignoring...");
						}
						else
						{
							userSubAccount.addUser(subUser);
						}
					}
				}
				
			}
			line = lineBuffer.next();
		}
		br.close();
		return bank;
	}
}
