package me.kingofmars4.murderer;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;
import me.kingofmars4.murderer.commands.Murderer;
import me.kingofmars4.murderer.files.FileMaps;
import me.kingofmars4.murderer.handlers.GameManager;
import me.kingofmars4.murderer.listeners.UseSign;
import me.kingofmars4.murderer.listeners.LobbyItems;
import me.kingofmars4.murderer.listeners.SignRewriting;

public class Main extends JavaPlugin implements CommandExecutor {
	
	public static Plugin plugin; public static Plugin getPlugin(){return plugin;}
	public static HashMap<String, List<UUID>> playersInGame = new HashMap<String, List<UUID>>();
	public static HashMap<String, String> gameStatus = new HashMap<String, String>();
	
	@Override
	public void onLoad() {
		loadConfigs();
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		
		enableCommands();
		enableListeners();
		loadCache();
	}
	
	@Override
	public void onDisable() {
		GameManager.get().forceShutdown();
	}
	
	
	public void loadCache() {
		GameManager.get().loadCache();
	}
	
	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	/*public static Scoreboard board = manager.getNewScoreboard();
	public static Team murderer = board.registerNewTeam("murderer");
	public static Team bystander = board.registerNewTeam("bystander");*/
	
	public void loadConfigs() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		FileMaps.setup();
		FileMaps.get().options().copyDefaults(true);
		FileMaps.save();
		
		getLogger().info("Configuration files succefully loaded.");
	}
	
	public void enableCommands() {
		getCommand("teste").setExecutor(this);
		getCommand("murderer createmap").setExecutor(new Murderer());
		getCommand("murderer deletemap").setExecutor(new Murderer());
		getCommand("murderer setspawn").setExecutor(new Murderer());
		getCommand("murderer setfragmentspawn").setExecutor(new Murderer());
		getCommand("murderer").setExecutor(new Murderer());
	}
	
	public void enableListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new SignRewriting(), this);
		pm.registerEvents(new UseSign(), this);
		pm.registerEvents(new LobbyItems(), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,  String[] args) {
		
		/*murderer.addPlayer(p);
		p.setScoreboard(board);
		p.sendMessage("adicionado a " + murderer.getDisplayName());
		System.out.println(murderer.getPlayers());
		System.out.println(murderer.getDisplayName());
		p.sendMessage(Messages.noPermission);*/
		
		return true;
	}
}
