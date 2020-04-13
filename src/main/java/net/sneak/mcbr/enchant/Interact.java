package net.sneak.mcbr.enchant;

import org.bukkit.block.EnchantingTable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(!(e.getClickedBlock().getState() instanceof EnchantingTable))
			return;
		e.setCancelled(true);
		
	}
}
