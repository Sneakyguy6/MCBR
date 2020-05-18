package net.sneak.mcbr;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class EndGame implements Listener {
	//private List<Team> teams;
	private boolean canMove;
	private Scoreboard b;
	
	public EndGame() {
		this.b = Plugin.getInstance().getServer().getScoreboardManager().getMainScoreboard();
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
		/*for(int i = 0; i < this.teams.size(); i++) {
			if(this.teams.get(i).equals(t)) {
				System.out.println("Eliminating team!");
				this.teams.remove(i);
				this.gameOver();
				return;
			}
		}*/
		t.unregister();
		this.gameOver();
	}
	
	private void gameOver() {
		if(b.getTeams().size() > 1)
			return;
		Bukkit.getScheduler().runTask(Plugin.getInstance(), new Runnable() {
			@Override
			public void run() {
				try {
					canMove = false;
					Location[] podium = {new Location(Bukkit.getWorlds().get(0), -16.5, 94.5, 60.5),
										 new Location(Bukkit.getWorlds().get(0), -18.5, 93.5, 57.5),
										 new Location(Bukkit.getWorlds().get(0), -21.5, 95.5, 57.5)};
					Plugin.getInstance().getServer().getOnlinePlayers().forEach((p) -> {
						if(!Iterables.getFirst(b.getTeams(), null).getEntries().contains(p.getDisplayName())) {
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
								new File(System.getProperty("user.dir") + "/restart.stamp").createNewFile();
								Scoreboard b = Plugin.getInstance().getServer().getScoreboardManager().getMainScoreboard();
								while(b.getTeams().size() != 0)
									b.getTeam(Iterables.getFirst(b.getTeams(), null).getName()).unregister();
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
		});
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMove(PlayerMoveEvent e) {
		e.setCancelled(!this.canMove);
	}
}
