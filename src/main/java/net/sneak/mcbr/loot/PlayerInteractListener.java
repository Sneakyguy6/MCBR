package net.sneak.mcbr.loot;

import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void onChestOpen(PlayerInteractEvent e) {
		if(!(e.getClickedBlock().getState() instanceof Chest))
			return;
		e.setCancelled(true);
	}
}
