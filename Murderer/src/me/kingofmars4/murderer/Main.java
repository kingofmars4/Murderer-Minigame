package me.kingofmars4.murderer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import me.kingofmars4.murderer.commands.MapCreating;
import me.kingofmars4.murderer.files.FileMaps;
import me.kingofmars4.murderer.listeners.PlayerInteract;
import me.kingofmars4.murderer.listeners.SignRewriting;
import me.kingofmars4.murderer.utils.Messages;

public class Main extends JavaPlugin implements CommandExecutor {
	
	public static Plugin plugin; public static Plugin getPlugin(){return plugin;}
	
	public static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	public static Team murderer = board.registerNewTeam("murderer");
	public static Team bystander = board.registerNewTeam("bystander");
	
	public void onEnable() {
		plugin = this;
		
		enableConfig();
		enableCustomConfigs();
		enableCommands();
		enableListeners();
	}
	
	public void enableConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void enableCustomConfigs(){
		FileMaps.setup();
		FileMaps.get().options().copyDefaults(true);
		FileMaps.save();
	}
	
	public void enableCommands() {
		getCommand("teste").setExecutor(this);
		getCommand("murderer createmap").setExecutor(new MapCreating());
		getCommand("murderer setspawn").setExecutor(new MapCreating());
		getCommand("murderer setfragmentspawn").setExecutor(new MapCreating());
		getCommand("murderer").setExecutor(new MapCreating());
	}
	
	public void enableListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new SignRewriting(), this);
		pm.registerEvents(new PlayerInteract(), this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,  String[] args) {
		
		Player p = (Player) sender;
		
		murderer.addPlayer(p);
		p.setScoreboard(board);
		p.sendMessage("adicionado a " + murderer.getDisplayName());
		System.out.println(murderer.getPlayers());
		System.out.println(murderer.getDisplayName());
		p.sendMessage(Messages.noPermission);
		
		return false;
	}
	

}
