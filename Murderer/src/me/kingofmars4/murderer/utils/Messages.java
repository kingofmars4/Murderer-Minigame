package me.kingofmars4.murderer.utils;

import org.bukkit.configuration.file.FileConfiguration;

import me.kingofmars4.murderer.Main;

public class Messages {
	
	private static FileConfiguration f = Main.getPlugin().getConfig();
	
	public static String pluginPrefix = U.color(f.getString("Options.pluginPrefix"));
	public static String noPermission = U.color(pluginPrefix + f.getString("Messages.noPermission"));
	public static String mustBePlayer = pluginPrefix + U.color(f.getString("Messages.mustBePlayer"));
	public static String noArguments = pluginPrefix + U.color(f.getString("Messages.noArguments"));
	public static String cmdDosntExist = pluginPrefix + U.color(f.getString("Messages.cmdDosntExist"));
}
