package net.sneak.mcbr;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.sneak.mcbr.armour.Armour;
import net.sneak.mcbr.inventory.mcbrInventory;
import net.sneak.mcbr.loot.PlayerInteractListener;
import net.sneak.mcbr.loot.table.LootTable;

public class Plugin extends JavaPlugin implements Listener {
	private static Plugin instance;
	public static Plugin getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		try {
			LootTable.init();
			this.registerEvents();
			//Bukkit.getServer().getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
			//Bukkit.getServer().getWorlds().get(0).game
			Bukkit.getServer().getWorlds().get(0).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
			Bukkit.getServer().getWorlds().get(0).setGameRule(GameRule.NATURAL_REGENERATION, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void registerEvents() {
		super.getServer().getPluginManager().registerEvents(new Armour(), this);
		super.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		super.getServer().getPluginManager().registerEvents(new mcbrInventory(), this);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		e.getPlayer().getInventory().getItemInMainHand();
		e.setCancelled(true);
	}

	@Override
	public void onDisable() {
		
	}
}
