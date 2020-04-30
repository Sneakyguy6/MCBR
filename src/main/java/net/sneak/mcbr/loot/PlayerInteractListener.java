package net.sneak.mcbr.loot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void onChestOpen(PlayerInteractEvent e) {
		if(e.getClickedBlock() == null)
			return;
		if(!(e.getClickedBlock().getState() instanceof Chest))
			return;
		e.getClickedBlock().getDrops().clear();
		Bukkit.getWorlds().get(0).spawnParticle(Particle.FIREWORKS_SPARK, e.getClickedBlock().getLocation(), 5);
		Bukkit.getWorlds().get(0).playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);
		e.getClickedBlock().setType(Material.AIR);
		e.setCancelled(true);
	}
}
