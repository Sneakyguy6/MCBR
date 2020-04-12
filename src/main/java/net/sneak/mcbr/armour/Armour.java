package net.sneak.mcbr.armour;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Armour implements Listener {
	
	@SuppressWarnings("incomplete-switch")
	@EventHandler
	public void onClickWithArmour(PlayerInteractEvent e) {
		if(!e.getItem().getType().equals(Material.IRON_CHESTPLATE) && !e.getItem().getType().equals(Material.GOLDEN_CHESTPLATE) && !e.getItem().getType().equals(Material.DIAMOND_CHESTPLATE))
			return;
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
		e.setCancelled(true);
		if(!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		if(p.getExp() >= 0.25f)
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_HURT, 20, 1);
		else
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT, 20, 1);
		double xpLost = (e.getDamage() * 4) / 100;
		if(xpLost > p.getExp()) {
			p.setExp(0);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 20, 1);
		}
		else
			p.setExp(p.getExp() - (float) xpLost);
		this.updateArmour(p);
	}
	
	private void updateArmour(Player p) {
		if(p.getExp() < 0.25f) {
			p.getInventory().setHelmet(null);
			p.getInventory().setHelmet(null);
			p.getInventory().setHelmet(null);
			p.getInventory().setHelmet(null);
		} else if(p.getExp() < 0.50f) {
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		} else if(p.getExp() < 0.75f) {
			p.getInventory().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			p.getInventory().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			p.getInventory().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			p.getInventory().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
		} else {
			p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		}
	}
}
