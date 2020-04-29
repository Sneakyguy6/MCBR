package net.sneak.mcbr;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.sneak.mcbr.enchant.EnchantInventory;
import net.sneak.mcbr.enchant.Interact;
import net.sneak.mcbr.join.McbrSocket;
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
			McbrSocket.init();
			this.registerEvents();
			Bukkit.getServer().getWorlds().get(0).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
			Bukkit.getServer().getWorlds().get(0).setGameRule(GameRule.NATURAL_REGENERATION, false);
			Bukkit.getServer().getWorlds().get(0).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void registerEvents() {
		super.getServer().getPluginManager().registerEvents(new Armour(), this);
		super.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
		mcbrInventory inv = new mcbrInventory();
		super.getServer().getPluginManager().registerEvents(inv, this);
		super.getServer().getPluginManager().registerEvents(new Elytra(inv), this);
		super.getServer().getPluginManager().registerEvents(new Health(), this);
		EnchantInventory temp = new EnchantInventory();
		super.getServer().getPluginManager().registerEvents(temp, this);
		super.getServer().getPluginManager().registerEvents(new Interact(temp), this);
		super.getServer().getPluginManager().registerEvents(new DeathAndRespawn(), this);
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
