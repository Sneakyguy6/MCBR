package net.sneak.mcbr.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class mcbrInventory implements Listener {
	
	public mcbrInventory() {
		Bukkit.getOnlinePlayers().forEach((player) -> {
			this.addInventoryItems(player);
		});
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemClick(InventoryClickEvent e) {
		if((e.isRightClick() || e.isLeftClick() || e.getAction().equals(InventoryAction.HOTBAR_SWAP)) && e.getSlot() >= 9)
			e.setCancelled(true);
		if(e.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD) || e.isShiftClick() || e.getClick().equals(ClickType.DOUBLE_CLICK))
			e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		this.addInventoryItems(e.getPlayer());
	}
	
	private void addInventoryItems(Player p) {
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		for(int i = 9; i < 36; i++)
			p.getInventory().setItem(i, item);
	}
}
