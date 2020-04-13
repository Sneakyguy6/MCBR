package net.sneak.mcbr.loot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class ChestScanner {
	public static List<Location> locate() {
		List<Location> chests = new ArrayList<Location>();
		for (int i = -256; i <= 256; i++) {
			for (int k = -256; k <= 256; k++) {
				for (int j = 6; j <= 255; j++) {
					Location l = new Location(Bukkit.getWorlds().get(0), i, j, k);
					if (l.getBlock().getBlockData().getMaterial().equals(Material.CHEST))
						chests.add(l);
				}
			}
		}

		return chests;
	}

	/*
	 * public static void writeToFile() { 
	 * 		File file = new File(Plugin.getInstance().getDataFolder().getAbsolutePath() + "/" +
	 * 		Bukkit.getWorlds().get(0) + ".csv");
	 * }
	 */
}
