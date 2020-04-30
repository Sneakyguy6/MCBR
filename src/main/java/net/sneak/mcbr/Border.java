package net.sneak.mcbr;

import org.bukkit.Bukkit;

public class Border {
	public static void run() {
		for(int i = 0; i < 5; i++) {
			Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			}, i * 1 * 20); //where 1 is the delay in seconds
		}
	}
}
