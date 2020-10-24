package me.kingofmars4.murderer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.kingofmars4.murderer.files.FileMaps;
import me.kingofmars4.murderer.utils.Messages;
import me.kingofmars4.murderer.utils.U;

public class MapCreating implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,  String[] args) {
		if (!(sender instanceof Player)) { sender.sendMessage(Messages.mustBePlayer); return true; }
		Player p = (Player) sender;
		
		if (!p.hasPermission("murderer.staff")) { p.sendMessage(Messages.noPermission); return true; }
		if (cmd.getName().equalsIgnoreCase("murderer")) {
			if (args.length==0) { p.sendMessage(Messages.noArguments); return true; } else { switch(args[0].toLowerCase()) {
			
			case "createmap":
				if (!(args.length == 2)) { p.sendMessage(Messages.pluginPrefix + U.color("&cCorrect usage: &e/murderer createmap (name)")); } else {
					p.sendMessage(Messages.pluginPrefix + U.color("&aStarting map creation..."));
					if (FileMaps.get().isConfigurationSection(args[1])) { p.sendMessage(Messages.pluginPrefix + U.color("&e'%map' &calredy exists! ".replaceAll("%map", args[1]))); p.sendMessage(Messages.pluginPrefix + U.color("&cCancelling map creation..."));; return true; }
					FileMaps.get().createSection(args[1]);
					FileMaps.save();
					p.sendMessage(Messages.pluginPrefix + U.color("&aSuccesefully created map &e'%map'&a!".replaceAll("%map", args[1])));
					p.sendMessage(U.color("&cATTENTION! THE MAP NAME IS CASE-SENSITIVE!"));
				} break;
				
				
			case "setspawn":
				if (!(args.length == 3)) { p.sendMessage(Messages.pluginPrefix + U.color("&cCorrect usage: &e/murderer setspawn (map) (number)")); } else {
					if (FileMaps.get().isConfigurationSection(args[1])) {
						int number;
						if (U.isInt(args[2])) { number = Integer.parseInt(args[2]); } else { p.sendMessage(Messages.pluginPrefix + U.color("&e'%args' &cisnt a number!".replaceAll("%args", args[2]))); return true; }
						double x = p.getLocation().getX(); double y = p.getLocation().getX(); double z = p.getLocation().getZ();
						FileMaps.get().set(args[1]+".spawnpoints."+number+".x", x); FileMaps.get().set(args[1]+".spawnpoints."+number+".y", y); FileMaps.get().set(args[1]+".spawnpoints."+number+".z", z);
						FileMaps.save();
						p.sendMessage(Messages.pluginPrefix + U.color("&aYour current position has been set as &e'%map' &6#%number spawnpoint&a!".replaceAll("%map", args[1]).replaceAll("%number", number+"")));
					} else { p.sendMessage(Messages.pluginPrefix + U.color("&cMap &e'%map' &cdoes not exist!".replaceAll("%map", args[1]))); }
				} break;
				
				
			case "setfragmentspawn":
				if (!(args.length == 3)) { p.sendMessage(Messages.pluginPrefix + U.color("&cCorrect usage: &e/murderer setfragmentspawn (map) (number)")); } else {
					if (FileMaps.get().isConfigurationSection(args[1])) {
						int number;
						if (U.isInt(args[2])) { number = Integer.parseInt(args[2]); } else { p.sendMessage(Messages.pluginPrefix + U.color("&e'%args' &cisnt a number!".replaceAll("%args", args[2]))); return true; }
						double x = p.getLocation().getX(); double y = p.getLocation().getX(); double z = p.getLocation().getZ();
						FileMaps.get().set(args[1]+".fragmentspawnpoints."+number+".x", x); FileMaps.get().set(args[1]+".fragmentspawnpoints."+number+".y", y); FileMaps.get().set(args[1]+".fragmentspawnpoints."+number+".z", z);
						FileMaps.save();
						p.sendMessage(Messages.pluginPrefix + U.color("&aYour current position has been set as &e'%map' &6#%number fragment spawnpoint&a!".replaceAll("%map", args[1]).replaceAll("%number", number+"")));
					} else { p.sendMessage(Messages.pluginPrefix + U.color("&cMap &e'%map' &cdoes not exist!".replaceAll("%map", args[1]))); }
				} break;
				
			default: p.sendMessage(Messages.cmdDosntExist); return true;
			}
			}
		}
		return true;
	}

}
