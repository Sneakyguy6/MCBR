package net.sneak.mcbr.enchant;

import java.util.Map;
import java.util.NoSuchElementException;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import net.md_5.bungee.api.ChatColor;

public class Interact implements Listener {
	private EnchantInventory enchantInventory;
	
	public Interact(EnchantInventory e) {
		this.enchantInventory = e;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(!(e.getWhoClicked().getOpenInventory().equals(this.enchantInventory.getCurrentViews().get(e.getWhoClicked()))))
			return;
		if(e.getCurrentItem() == null)
			return;
		e.setCancelled(true);
		if(e.getCurrentItem().getType() != Material.ENCHANTED_BOOK)
			return;
		Enchantment ent; 
		try {
			ent = e.getCurrentItem().getItemMeta().getEnchants().keySet().iterator().next();
		} catch (NoSuchElementException | ClassCastException e1) {
			return;
		}
		int noOfLapis = 0;
		for(int i = 0; i < 9; i++)
			if(e.getWhoClicked().getInventory().getItem(i) != null)
				if(e.getWhoClicked().getInventory().getItem(i).getType() == Material.LAPIS_LAZULI)
					noOfLapis += e.getWhoClicked().getInventory().getItem(i).getAmount();
		for(int i = 0; i < 9; i++) {
			ItemStack item = e.getWhoClicked().getInventory().getItem(i);
			Map<Enchantment, Integer> enchants;
			try {
				enchants = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants();
			} catch (ClassCastException e1) {
				continue;
			}
			if(enchants.size() == 1 && enchants.keySet().iterator().next().equals(ent) && (enchants.values().iterator().next() == e.getCurrentItem().getItemMeta().getEnchants().get(ent) - 1)) {
				if(noOfLapis < Math.pow(2, e.getCurrentItem().getItemMeta().getEnchants().get(ent))) {
					e.getWhoClicked().sendMessage(ChatColor.RED + "You do not have enough lapis.");
					((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
					return;
				}
				EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
				int level = meta.getStoredEnchants().get(ent) + 1;
				meta.removeStoredEnchant(ent);
				meta.addStoredEnchant(ent, level, false);
				item.setItemMeta(meta);
				((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
				e.getWhoClicked().getInventory().setItem(i, item);
				noOfLapis = (int) Math.round(Math.pow(2, e.getCurrentItem().getItemMeta().getEnchants().get(ent)));
				for(int j = 0; j < 9; j++) {
					if(e.getWhoClicked().getInventory().getItem(j) == null)
						continue;
					ItemStack temp = e.getWhoClicked().getInventory().getItem(j);
					if(temp.getType() == Material.LAPIS_LAZULI) {
						if(temp.getAmount() <= noOfLapis) {
							noOfLapis -= temp.getAmount();
							temp.setAmount(0);
						} else {
							temp.setAmount(temp.getAmount() - noOfLapis);
							noOfLapis = 0;
						}
					}
				}
				this.enchantInventory.updateInventory(this.enchantInventory.getCurrentViews().get(e.getWhoClicked()).getTopInventory(), e.getWhoClicked());
				return;
			}
		}
		((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1, 1);
	}
}
