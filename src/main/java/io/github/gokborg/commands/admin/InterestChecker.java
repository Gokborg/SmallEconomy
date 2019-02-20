package io.github.gokborg.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.gokborg.components.Account;
import io.github.gokborg.components.Bank;
import io.github.gokborg.components.User;

public class InterestChecker extends BukkitRunnable
{
	private long prevTime;
	private Bank bank;
	public InterestChecker(Bank bank)
	{
		this.bank = bank;
	}
	@Override
	public void run()
	{
		long difference = System.currentTimeMillis() - prevTime;
		if(bank.getInterestTime() == 0 || bank.getInterestRate() == 0)
		{
			return;
		}
		if(difference > bank.getInterestTime())
		{
			prevTime = System.currentTimeMillis();
			for(User user : bank.getAllUsers())
			{
				Player player = Bukkit.getPlayer(user.getUUID());
				if(player != null)
				{
					player.sendMessage(ChatColor.GREEN + "Interest has been applied! Enjoy the extra money!");
					user.getMainAccount().add(Math.round(user.getMainAccount().getTotal()*bank.getInterestRate()));
					for(Account acc : user.getAllAccounts())
					{
						acc.add(Math.round(acc.getTotal() * bank.getInterestRate()));
					}
				}
			}
		}
	}
}
