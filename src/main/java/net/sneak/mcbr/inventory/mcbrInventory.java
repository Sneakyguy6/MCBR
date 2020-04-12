package net.sneak.mcbr.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class mcbrInventory implements Listener {
	@EventHandler
	public void onPlayerPickup(EntityPickupItemEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		boolean full = true;
		for(int i = 0; i < 9; i++) {
			if(p.getInventory().getItem(i) != null) {
				full = false;
				break;
			}
		}
		if(full)
			e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemClick(InventoryClickEvent e) {
		if(e.getSlot() >= 9)
			e.setCancelled(true);
		//if(e.getAction().equals(InventoryAction.Sw))
	}
}
