package net.sneak.mcbr.armour;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class Armour implements Listener {
	
	@SuppressWarnings("incomplete-switch")
	@EventHandler
	public void onClickWithArmour(PlayerInteractEvent e) {
		if(!e.getItem().getType().equals(Material.IRON_CHESTPLATE) && !e.getItem().getType().equals(Material.GOLDEN_CHESTPLATE) && !e.getItem().getType().equals(Material.DIAMOND_CHESTPLATE))
			return;
		e.setCancelled(true);
		switch(e.getItem().getType())
		{
		case IRON_CHESTPLATE:
			if(e.getPlayer().getExp() < 0.67f)
				e.getPlayer().setExp(e.getPlayer().getExp() + 0.33f);
			else
				e.getPlayer().setExp(0.99f);
			break;
		case GOLDEN_CHESTPLATE:
			if(e.getPlayer().getExp() < 0.50f)
				e.getPlayer().setExp(e.getPlayer().getExp() + 0.50f);
			else
				e.getPlayer().setExp(0.99f);
			break;
		case DIAMOND_CHESTPLATE:
			if(e.getPlayer().getExp() < 0.20f)
				e.getPlayer().setExp(e.getPlayer().getExp() + 0.80f);
			else
				e.getPlayer().setExp(0.99f);
			break;
		}
		this.updateArmour(e.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDamageEvent(EntityDamageEvent e) {
		//e.setCancelled(true);
		if(!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		if(p.getExp() >= 0.25f)
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_HURT, 20, 1);
		else
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT, 20, 1);
		if(p.getExp() == 0f)
			return;
		e.setDamage(0);
		double xpLost = (e.getDamage() * 4) / 100;
		if(xpLost > p.getExp()) {
			p.setExp(0);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 10, 1);
		}
		else
			p.setExp(p.getExp() - (float) xpLost);
		this.updateArmour(p);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().setLevel(0);
		e.getPlayer().setExp(0);
	}
	
	private void updateArmour(Player p) {
		if(p.getExp() == 0f) {
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
		} else if(p.getExp() < 0.33f) {
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		} else if(p.getExp() < 0.66f) {
			p.getInventory().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			p.getInventory().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
			p.getInventory().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
			p.getInventory().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
		} else {
			p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		}
	}
}
