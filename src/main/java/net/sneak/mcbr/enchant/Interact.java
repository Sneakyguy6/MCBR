package net.sneak.mcbr.enchant;

import org.bukkit.NamespacedKey;
import org.bukkit.block.EnchantingTable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import net.sneak.mcbr.Plugin;

public class Interact implements Listener {
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(!(e.getClickedBlock().getState() instanceof EnchantingTable))
			return;
		e.setCancelled(true);
		
	}
	
	public void addRecipe() {
		Recipe recipe = new ShapelessRecipe(new NamespacedKey(Plugin.getInstance(), "enchanted_item"), result);
	}
}
