package io.github.gokborg.file;

import org.bukkit.scheduler.BukkitRunnable;

import io.github.gokborg.SmallEconomy;

public class Saver extends BukkitRunnable
{
	long time;
	private SmallEconomy smallEco;
	
	public Saver(SmallEconomy smallEco)
	{
		this.smallEco = smallEco;
	}
	@Override
	public void run() 
	{
		long diff = System.currentTimeMillis() - time;
		if(diff >= 600000)
		{
			smallEco.saveEconomy();
			time = System.currentTimeMillis();
			System.out.println("[SmallEconomy] Saving all economy data.");
		}
	}
}
