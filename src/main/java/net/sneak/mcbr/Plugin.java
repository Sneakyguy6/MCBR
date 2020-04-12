package net.sneak.mcbr;

import org.bukkit.plugin.java.JavaPlugin;

import net.sneak.mcbr.armour.Armour;
import net.sneak.mcbr.loot.table.LootTable;

public class Plugin extends JavaPlugin {
	private static Plugin instance;
	public static Plugin getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		try {
			LootTable.init();
			super.getServer().getPluginManager().registerEvents(new Armour(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		
	}
}
