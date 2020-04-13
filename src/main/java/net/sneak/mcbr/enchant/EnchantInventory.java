package net.sneak.mcbr.enchant;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.sneak.mcbr.Plugin;

public class EnchantInventory implements Listener {
	@EventHandler
	public void onOpen(InventoryOpenEvent e) {
		if(e.getInventory().getType() != InventoryType.ENCHANTING)
			return;
		e.setCancelled(true);
		Bukkit.getScheduler().runTask(Plugin.getInstance(), new Runnable() {
			@Override
			public void run() {
				Inventory inventory = Bukkit.createInventory(e.getPlayer(), 81, "use the power of ur acutal dad");
				int inventoryIndex = 0;
				for(int i = 0; i < 9; i++) {
					if(e.getPlayer().getInventory().getItem(i) == null)
						continue;
					ItemStack item = e.getPlayer().getInventory().getItem(i);
					if(item.getType() != Material.ENCHANTED_BOOK)
						continue;
					Enchantment ent = item.getEnchantments().keySet().iterator().next();
					for(; inventoryIndex < item.getEnchantments().get(item.getEnchantments().keySet().iterator().next()); inventoryIndex++) {
						
					}
				}
				e.getPlayer().openInventory(inventory);
			}
		});
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void inAnvilInventory(InventoryClickEvent e) {
		if(e.getView().getType() != InventoryType.ANVIL)
			return;
		
	}
}
