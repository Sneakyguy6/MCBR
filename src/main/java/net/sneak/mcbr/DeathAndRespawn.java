package net.sneak.mcbr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

public class DeathAndRespawn implements Listener {
	@EventHandler
	public void onBeaconClick(PlayerInteractEvent e) {
		if(e.getClickedBlock().getType() != Material.BEACON)
			return;
		new Thread(new Runnable() {
			Location beacon = e.getClickedBlock().getLocation();
			@Override
			public void run() {
				try {
					for(int i = 255; i > beacon.getY() + 1; i--) {
						Bukkit.getWorlds().get(0).spawnEntity(new Location(Bukkit.getWorlds().get(0), beacon.getX(), i, beacon.getZ()), EntityType.FIREWORK);
						Thread.sleep(500);
					}
					for(String i : Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(e.getPlayer().getDisplayName()).getEntries()) {
						Player p = Bukkit.getPlayer(i);
						if(p.getGameMode() == GameMode.SPECTATOR) {
							p.teleport(beacon.add(0, 1, 0));
							p.setGameMode(GameMode.SURVIVAL);
						}
					}
					Bukkit.getWorlds().get(0).spawnParticle(Particle.EXPLOSION_NORMAL, beacon.add(0, 1, 0), 1);
					Bukkit.getWorlds().get(0).playSound(beacon.add(0, 1, 0), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player) || e.getCause() == DamageCause.ENTITY_ATTACK)
			return;
		e.setCancelled(this.handle((Player) e.getEntity(), e.getDamage()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeathByEntity(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player) || e.getCause() != DamageCause.ENTITY_ATTACK)
			return;
		e.setCancelled(this.handle((Player) e.getEntity(), e.getDamage(), (Player) e.getDamager()));
	}
	
	private boolean handle(Player p, double damage, Player... killer) {
		if(damage > p.getHealth()) {
			p.setGameMode(GameMode.SPECTATOR);
			if(killer.length == 1)
				Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.YELLOW + " was killed by " + killer[0] + ChatColor.YELLOW + " using " + ChatColor.AQUA + killer[0].getInventory().getItemInMainHand().getItemMeta().getDisplayName());
			else
				Bukkit.broadcastMessage(p.getDisplayName() + ChatColor.YELLOW + "died");
			return true;
		}
		return false;
	}
}
