package net.sneak.mcbr;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.Team;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class EndGame implements Listener {
	private List<Team> teams;
	private boolean canMove;
	
	public EndGame() {
		this.teams = new LinkedList<Team>();
		this.canMove = true;
	}
	
	public void onDeath(Player p) {
		Team t = Plugin.getInstance().getServer().getScoreboardManager().getMainScoreboard().getEntryTeam(p.getDisplayName());
		for(String i : t.getEntries())
			if(Plugin.getInstance().getServer().getPlayer(i).getGameMode() != GameMode.SPECTATOR)
				return;
		Plugin.getInstance().getServer().broadcastMessage(t.getColor() + t.getDisplayName() + ChatColor.YELLOW + " has been eliminated!");
		Bukkit.getOnlinePlayers().forEach((player) -> {
			player.sendTitle(t.getColor() + t.getDisplayName(), ChatColor.YELLOW + " has been eliminated!", 10, 50, 10);
		});
		for(int i = 0; i < this.teams.size(); i++) {
			if(this.teams.get(i).equals(t)) {
				this.teams.remove(i);
				this.gameOver();
				return;
			}
		}
	}
	
	private void gameOver() {
		if(this.teams.size() > 1)
			return;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					canMove = false;
					Location[] podium = {new Location(Bukkit.getWorlds().get(0), -16, 94, 60),
										 new Location(Bukkit.getWorlds().get(0), -18, 93, 57),
										 new Location(Bukkit.getWorlds().get(0), -21, 95, 57)};
					Plugin.getInstance().getServer().getOnlinePlayers().forEach((p) -> {
						if(!teams.get(0).equals(Plugin.getInstance().getServer().getScoreboardManager().getMainScoreboard().getEntryTeam(p.getDisplayName()))) {
							p.setGameMode(GameMode.SPECTATOR);
							p.teleport(new Location(Bukkit.getWorlds().get(0), -19, 97, 49));
						}
						else {
							p.setGameMode(GameMode.ADVENTURE);
							p.teleport(podium[0]);
							podium[0] = podium[1];
							podium[1] = podium[2];
						}
					});
					Bukkit.getScheduler().runTaskLater(Plugin.getInstance(), new Runnable() {
						@Override
						public void run() {
							try {
								endGame();
								ByteArrayDataOutput out;
								for (Player i : Plugin.getInstance().getServer().getOnlinePlayers()) {
									out = ByteStreams.newDataOutput();
									out.writeUTF("Connect");
									out.writeUTF("lobby");
									i.sendPluginMessage(Plugin.getInstance(), "BungeeCord", out.toByteArray());
								}
								Bukkit.shutdown();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}, 7 * 20);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void endGame() throws IOException {
		new File(System.getProperty("user.dir") + "/restart.stamp").createNewFile();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMove(PlayerMoveEvent e) {
		e.setCancelled(!this.canMove);
	}
}
