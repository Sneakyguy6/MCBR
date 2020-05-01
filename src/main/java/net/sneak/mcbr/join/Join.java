package net.sneak.mcbr.join;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import net.sneak.mcbr.MoveBus;
import net.sneak.mcbr.Plugin;

public class Join implements Listener {
	private static Join instance;
	private int counter;
	private Map<String, String> settings;
	private String[] players;
	private String[] teamNames;

	public static Join getInstance() {
		return instance;
	}

	public Join() {
		instance = this;
		this.settings = new HashMap<String, String>();
		this.counter = 0;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		if(this.counter == this.players.length) {
			e.getPlayer().setGameMode(GameMode.SPECTATOR);
		}
		else {
			e.getPlayer().setGameMode(GameMode.SURVIVAL);
			int index = 0;
			for(; index < players.length; index++)
				if(e.getPlayer().getUniqueId().toString().equals(this.players[index]))
					break;
			Plugin.getInstance().getServer().getScoreboardManager().getMainScoreboard().getTeam(this.teamNames[index / Integer.parseInt(this.settings.get("TEAMSIZE"))]).addEntry(e.getPlayer().getDisplayName());
			this.counter++;
			if(this.counter == this.players.length)
				MoveBus.move();
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSpawn(PlayerSpawnLocationEvent e) {
		e.setSpawnLocation(new Location(Bukkit.getWorlds().get(0), 0, 244, -236));
	}

	public void setSettingsAndPlayerList(String in) {
		String[] temp = in.split(" ");
		String[] settings = temp[0].split(",");
		String[] settingsValues = temp[1].split(",");
		this.teamNames = temp[2].split(",");
		this.players = temp[3].split(",");
		for(int i = 0; i < settings.length; i++)
			this.settings.put(settings[i], settingsValues[i]);
		this.setupTeams();
	}
	
	private void setupTeams() {
		Scoreboard s = Plugin.getInstance().getServer().getScoreboardManager().getMainScoreboard();
		int counter = 0;
		for(String i : this.teamNames) {
			s.registerNewTeam(i);
			s.getTeam(i).setColor(ChatColor.values()[counter % ChatColor.values().length]);
			s.getTeam(i).setAllowFriendlyFire(false);
			counter++;
		}
	}
}
