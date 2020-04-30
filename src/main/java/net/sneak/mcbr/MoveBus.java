package net.sneak.mcbr;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class MoveBus {
	private static Block[][][] bus;
	public static void move() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {//from (-16, 250, 8) to (15, 256, -16) //starts (-16, 250, -229) ends (-16, 250, ?)
					for(int i = 0; i <= 480; i++) {
						int z = -229 + i;
						Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), new Runnable() {
							@Override
							public void run() {
								paste(z);
							}
						}, i * 10);
					}			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static void init() {
		bus = new Block[32][8][25];
		for(int i = -16; i <= 15; i++)
			for(int j = 249; j <= 256; j++)
				for(int k = 8; k >= -16; k--)
					bus[i + 16][j - 249][8 - k] = Bukkit.getWorlds().get(0).getBlockAt(i, j, k);
	}
	
	private static void paste(int beginningZValue) {
		for(int i = -16; i <= 15; i++)
			for(int j = 240; j <= 247; j++)
				for(int k = beginningZValue; k >= beginningZValue - 24; k--) {
					Bukkit.getServer().getWorlds().get(0).getBlockAt(i, j, k).setType(bus[i + 16][j - 240][beginningZValue - k].getType());
					Bukkit.getServer().getWorlds().get(0).getBlockAt(i, j, k).setBlockData((bus[i + 16][j - 240][beginningZValue - k].getBlockData()), true);
				}
		Bukkit.getOnlinePlayers().forEach((p) -> {
			if(!p.isGliding() && p.getInventory().getItem(38).getType() == Material.ELYTRA)
				p.teleport(p.getLocation().add(0, 0, 1));
			//p.getLocation().add(0, 0, 1);
		});
	}
}
//System.out.println(i + " " + j + " " + k + " : " + (i + 16) + " " + (j - 250) + " " + (beginningZValue - k));