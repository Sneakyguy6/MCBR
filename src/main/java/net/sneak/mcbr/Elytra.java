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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().getInventory().setItem(38, new ItemStack(Material.ELYTRA));
		e.getPlayer().setInvulnerable(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent e) {
		try {
			if(((Player) e.getEntity()).getInventory().getChestplate().getType() == Material.ELYTRA)
				e.setDamage(0);
		} catch (NullPointerException | ClassCastException e1) {
		}
	}
	
	@EventHandler
	public void onFly(EntityToggleGlideEvent e) {
		Bukkit.getScheduler().runTask(Plugin.getInstance(), new Runnable() {
			@Override
			public void run() {
				try {
					if(!e.isGliding() && e.getEntity().getLocation().getY() < 100) {
						((Player) e.getEntity()).getInventory().setItem(38, inventoryLockItem);
						((Player) e.getEntity()).setInvulnerable(false);
					}
				} catch (ClassCastException e1) {
				}
			}
		});
	}
}
