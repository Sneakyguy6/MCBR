package net.sneak.mcbr;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.google.common.collect.Iterables;

import net.sneak.mcbr.enchant.EnchantInventory;
import net.sneak.mcbr.enchant.Interact;
import net.sneak.mcbr.join.Join;
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
			super.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
			LootTable.init();
			McbrSocket.init();
			this.registerEvents();
			Bukkit.getServer().getWorlds().get(0).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
			Bukkit.getServer().getWorlds().get(0).setGameRule(GameRule.NATURAL_REGENERATION, false);
			Bukkit.getServer().getWorlds().get(0).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
			MoveBus.init();
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
		super.getServer().getPluginManager().registerEvents(new Join(), this);
		EndGame endGame = new EndGame();
		super.getServer().getPluginManager().registerEvents(endGame, this);
		super.getServer().getPluginManager().registerEvents(new DeathAndRespawn(endGame), this);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		e.getPlayer().getInventory().getItemInMainHand();
		e.setCancelled(true);
	}

	@Override
	public void onDisable() {
		Scoreboard b = super.getServer().getScoreboardManager().getMainScoreboard();
		while(b.getTeams().size() != 0)
			b.getTeam(Iterables.getFirst(b.getTeams(), null).getName()).unregister();
		try {
			McbrSocket.getInstance().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
