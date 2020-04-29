package net.sneak.mcbr;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class Health implements Listener {
	private Map<Player, Long> cooldowns;
	
	public Health() {
		this.cooldowns = new HashMap<Player, Long>();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		this.cooldowns.put(e.getPlayer(), 0L);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClick(PlayerInteractEvent e) {
		if(e.getItem() == null || e.getPlayer().getHealth() == 20)
			return;
		if(this.cooldowns.get(e.getPlayer()) > System.currentTimeMillis()) {
			e.getPlayer().sendMessage(ChatColor.GREEN + "You cannot eat for " + ChatColor.DARK_GREEN + ((this.cooldowns.get(e.getPlayer()) - System.currentTimeMillis()) / 1000) + ChatColor.GREEN + " seconds" );
			return;
		}
		e.setCancelled(true);
		double health = e.getPlayer().getHealth();
		switch(e.getItem().getType())
		{
		case COOKED_BEEF:
			health += 5;
			break;
		case APPLE:
			health += 1;
			break;
		case COOKED_CHICKEN:
			health += 3;
			break;
		case BREAD:
			health += 2;
			break;
		default:
			e.setCancelled(false);
			return;
		}
		if(health > 20)
			health = 20;
		e.getPlayer().setHealth(health);
		e.getItem().setAmount(e.getItem().getAmount() - 1);
		Bukkit.getWorlds().get(0).playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1);
		Bukkit.getWorlds().get(0).spawnParticle(Particle.HEART, e.getPlayer().getLocation().add(0, 2, 0), 20);
		this.cooldowns.put(e.getPlayer(), System.currentTimeMillis() + 3000);
	}
}
