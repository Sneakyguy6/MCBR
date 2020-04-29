package net.sneak.mcbr.join;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import net.sneak.mcbr.Plugin;

public class Join implements Listener {
	private static Join instance;

	private Map<String, String> settings;
	private String[] players;
	private String[] teamNames;

	public static Join getInstance() {
		return instance;
	}

	public Join() {
		instance = this;
		this.settings = new HashMap<String, String>();
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent e) {
		int index = 0;
		for(; index < players.length; index++)
			if(e.getPlayer().getUniqueId().toString().equals(this.players[index]))
				break;
		Plugin.getInstance().getServer().getScoreboardManager().getMainScoreboard().getTeam(this.teamNames[index / Integer.parseInt(this.settings.get("TEAMSIZE"))]).addEntry(e.getPlayer().getDisplayName());
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
		}
	}
}
