package net.sneak.mcbr;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Health implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClick(PlayerInteractEvent e) {
		if(e.getItem() == null)
			return;
		if(e.getPlayer().getHealth() == 20)
			return;
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
	}
}
