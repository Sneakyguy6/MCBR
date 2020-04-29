package net.sneak.mcbr;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent e) {
		try {
			if(((Player) e.getEntity()).getInventory().getChestplate().getType() == Material.ELYTRA)
				e.setDamage(0);
			System.out.println("test");
		} catch (ClassCastException e1) {
			//e1.printStackTrace();
		}
	}
	
	@EventHandler
	public void onFly(EntityToggleGlideEvent e) {
		Bukkit.getScheduler().runTask(Plugin.getInstance(), new Runnable() {
			@Override
			public void run() {
				try {
					if(!e.isGliding() && e.getEntity().getLocation().getY() < 100)
						((Player) e.getEntity()).getInventory().setItem(38, inventoryLockItem);
				} catch (ClassCastException e1) {
				}
			}
		});
	}
}
