package me.kingofmars4.murderer.files;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.kingofmars4.murderer.Main;
import me.kingofmars4.murderer.utils.U;

public class FileMaps {
	private static File file;
	private static FileConfiguration fileMaps;
	
	public static void setup () {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("Murderer").getDataFolder(), "maps.yml");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				
			}
		}
		
		fileMaps = YamlConfiguration.loadConfiguration(file);
	}
	
	public static FileConfiguration get() {
		return fileMaps;
	}
	
	public static void save() {
		try {
			fileMaps.save(file);
		} catch (IOException e) {
			Main.getPlugin().getServer().getConsoleSender().sendMessage(U.color("&cIt was not possible to save maps.yml"));
		}
	}
	
	public static void reload() {
		fileMaps = YamlConfiguration.loadConfiguration(file);
	}
}
