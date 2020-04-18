package net.sneak.mcbr.enchant;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import net.sneak.mcbr.Plugin;

public class EnchantInventory implements Listener {
	private Map<HumanEntity, InventoryView> views;
	
	public EnchantInventory() {
		this.views = new HashMap<HumanEntity, InventoryView>();
	}
	
	@EventHandler
	public void onOpen(InventoryOpenEvent e) {
		if(e.getInventory().getType() != InventoryType.ENCHANTING)
			return;
		e.setCancelled(true);
		Bukkit.getScheduler().runTask(Plugin.getInstance(), new Runnable() {
			@Override
			public void run() {
				/*InventoryView enchanterView = new InventoryView() {
					
					@Override
					public InventoryType getType() {
						return InventoryType.CHEST;
					}
					
					@Override
					public Inventory getTopInventory() {
						PlayerInventory inventory = (PlayerInventory) Bukkit.createInventory(e.getPlayer(), 54, "[example]");
						updateInventory(inventory, (Player) e.getPlayer());
						return inventory;
					}
					
					@Override
					public String getTitle() {
						return "[example]";
					}
					
					@Override
					public HumanEntity getPlayer() {
						return e.getPlayer();
					}
					
					@Override
					public Inventory getBottomInventory() {
						PlayerInventory temp = (PlayerInventory) Bukkit.createInventory(e.getPlayer(), 9, "Hotbar");
						for(int i = 0; i < 9; i++)
							if(e.getPlayer().getInventory().getItem(i) != null)
								temp.setItem(i, e.getPlayer().getInventory().getItem(i));
						return temp;
					}
				};*/
				Inventory inventory = Bukkit.createInventory(e.getPlayer(), 54, "[insert funny phrase here]");
				updateInventory(inventory, (Player) e.getPlayer());
				//e.getPlayer().openInventory(enchanterView);
				//views.put(e.getPlayer(), enchanterView);
				views.put(e.getPlayer(), e.getPlayer().openInventory(inventory));
			}
		});
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if(this.views.containsKey(e.getPlayer()))
			this.views.remove(e.getPlayer());
	}
	
	public void updateInventory(Inventory inventory, HumanEntity p) {
		int inventoryIndex = 0;
		for(int i = 0; i < 9; i++) {
			if(p.getInventory().getItem(i) == null)
				continue;
			ItemStack item = p.getInventory().getItem(i);
			if(item.getType() != Material.ENCHANTED_BOOK)
				continue;
			//System.out.println(((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().size());
			//System.out.println("INDEX: " + inventoryIndex);
			Enchantment ent = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().keySet().iterator().next();
			ItemStack alreadyAquired = new ItemStack(Material.BOOK);
			ItemMeta meta = alreadyAquired.getItemMeta();
			meta.addEnchant(ent, 1, true);
			meta.setDisplayName("Aquired");
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
			alreadyAquired.setItemMeta(meta);
			for(; inventoryIndex < ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().get(ent) + (inventoryIndex - (inventoryIndex % 9)); inventoryIndex++) {
				inventory.setItem(inventoryIndex, new ItemStack(alreadyAquired));
				//int temp = meta.getEnchantLevel(alreadyAquired.getItemMeta().getEnchants().keySet().iterator().next());
				//meta.removeEnchant(ent);
				//meta.addEnchant(ent, temp, false);
			}
			ItemStack nextAvailable;
			if(((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().get(ent) < ent.getMaxLevel()) {
				nextAvailable = new ItemStack(Material.ENCHANTED_BOOK);
				meta = nextAvailable.getItemMeta();
				meta.addEnchant(ent, ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants().get(ent) + 1, false);
				meta.setDisplayName(Double.toString(Math.pow(2, meta.getEnchants().values().iterator().next())) + " lapis");
				nextAvailable.setItemMeta(meta);
			}
			else {
				nextAvailable = new ItemStack(Material.DIAMOND);
				meta = nextAvailable.getItemMeta();
				meta.addEnchant(ent, 1, true);
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
				meta.setDisplayName("Max level reached");
				nextAvailable.setItemMeta(meta);
				//inventoryIndex--;
			}
			inventory.setItem(inventoryIndex, nextAvailable);
			inventoryIndex += 9 - (inventoryIndex % 9);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void inAnvilInventory(InventoryClickEvent e) {
		if(e.getView().getType() != InventoryType.ANVIL)
			return;
	}
	
	public Map<HumanEntity, InventoryView> getCurrentViews() {
		return this.views;
	}
}
