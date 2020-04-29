package net.sneak.mcbr;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public class Elytra implements Listener {
	
	private ItemStack inventoryLockItem;
	public Elytra(mcbrInventory inv) {
		this.inventoryLockItem = inv.getinventoryLockItem();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().getInventory().setItem(38, new ItemStack(Material.ELYTRA));
	}
	
	@EventHandler
	public void onMove(PlayerToggleFlightEvent e) {
		if(!e.isFlying())
			e.getPlayer().getInventory().setItem(38, this.inventoryLockItem);
	}
}
