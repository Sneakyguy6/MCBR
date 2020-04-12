package net.sneak.mcbr;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
	private static Plugin instance;
	public static Plugin getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		try {
			if(!super.getDataFolder().exists())
				super.getDataFolder().createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		
	}
}
