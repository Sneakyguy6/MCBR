package net.sneak.mcbr;

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
	
	private ItemStack inventoryLockItem;
	public mcbrInventory() {
		this.inventoryLockItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = this.inventoryLockItem.getItemMeta();
		meta.setDisplayName(" ");
		this.inventoryLockItem.setItemMeta(meta);
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
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().getInventory().clear();
		this.addInventoryItems(e.getPlayer());
	}
	
	private void addInventoryItems(Player p) {
		for(int i = 9; i < 36; i++)
			p.getInventory().setItem(i, this.inventoryLockItem);
		//p.getInventory().setItem(38, item); handled by elytra class
	}
	
	public ItemStack getinventoryLockItem() {
		return this.inventoryLockItem;
	}
}
