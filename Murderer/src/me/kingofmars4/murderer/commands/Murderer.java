package me.kingofmars4.murderer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.kingofmars4.murderer.files.FileMaps;
import me.kingofmars4.murderer.gameHandlers.Game;
import me.kingofmars4.murderer.gameHandlers.GameManager;
import me.kingofmars4.murderer.utils.Messages;
import me.kingofmars4.murderer.utils.U;

public class Murderer implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,  String[] args) {
		if (!(sender instanceof Player)) { sender.sendMessage(Messages.mustBePlayer); return true; }
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("murderer")) {
			if (args.length==0) { p.sendMessage(Messages.noArguments); return true; } else { switch(args[0].toLowerCase()) {
			
			case "createmap":
				if (!p.hasPermission("murderer.staff")) { p.sendMessage(Messages.noPermission); return true; }
				if (!(args.length == 2)) { p.sendMessage(U.pluginPrefix + U.color("&cCorrect usage: &e/murderer createmap (name)")); } else {
					p.sendMessage(U.pluginPrefix + U.color("&aStarting map creation..."));
					if (FileMaps.get().isConfigurationSection(args[1])) { p.sendMessage(U.pluginPrefix + U.color("&e'%map' &calredy exists! ".replaceAll("%map", args[1]))); p.sendMessage(U.pluginPrefix + U.color("&cCancelling map creation..."));; return true; }

					GameManager.get().createGame(args[1], p.getLocation());
					
					p.sendMessage(U.pluginPrefix + U.color("&aSuccesefully created map &e'%map' &aand set your current position has the map spawn!".replaceAll("%map", args[1])));
				} break;
				
				
				
			case "deletemap":
				if (!p.hasPermission("murderer.staff")) { p.sendMessage(Messages.noPermission); return true; }
				if (!(args.length == 2)) { p.sendMessage(U.pluginPrefix + U.color("&cCorrect usage: &e/murderer deletemap (name)")); } else {
					p.sendMessage(U.pluginPrefix + U.color("&aStarting map deletion..."));
					if (!GameManager.get().isGame(args[1])) { p.sendMessage(U.pluginPrefix + U.color("&e'%map' &cdoes not exists! ".replaceAll("%map", args[1]))); p.sendMessage(U.pluginPrefix + U.color("&cCancelling map deltion..."));; return true; }

					GameManager.get().deleteGame(args[1]);
					p.sendMessage(U.pluginPrefix + U.color("&aSuccesefully deleted map &e'%map'&a!".replaceAll("%map", args[1])));
				} break;
				
				
				
			case "setspawn":
				if (!p.hasPermission("murderer.staff")) { p.sendMessage(Messages.noPermission); return true; }
				if (!(args.length == 3)) { p.sendMessage(U.pluginPrefix + U.color("&cCorrect usage: &e/murderer setspawn (map) (number)")); } else {
					if (GameManager.get().isGame(args[1])) {
						Game l = GameManager.get().getGame(args[1]);
						int id = Integer.parseInt(args[2]);
						if (!U.isInt(args[2])) { p.sendMessage(U.pluginPrefix + U.color("&e'%args' &cisnt a number!".replaceAll("%args", id+""))); return true; }
						int n = Integer.parseInt(args[2]);
						if (n<=0) { p.sendMessage(U.pluginPrefix + U.color("&e'%args' &cneeds to be a number above 0!".replaceAll("%args", id+""))); return true; }
						
						
						FileMaps.get().set("Maps."+l.getName()+".spawns."+id, U.serializeLoc(p.getLocation()));
						FileMaps.save();
						l.getSpawns().put(id, p.getLocation());
						p.sendMessage(U.pluginPrefix + U.color("&aYour current position has been set as &e'%map' &6#%number spawnpoint&a!".replaceAll("%map", l.getName()).replaceAll("%number", n+"")));
					} else { p.sendMessage(U.pluginPrefix + U.color("&cMap &e'%map' &cdoes not exist!".replaceAll("%map", args[1]))); }
				} break;
				
				
				
			case "setfragmentspawn":
				if (!p.hasPermission("murderer.staff")) { p.sendMessage(Messages.noPermission); return true; }
				if (!(args.length == 3)) { p.sendMessage(U.pluginPrefix + U.color("&cCorrect usage: &e/murderer setfragmentspawn (map) (number)")); } else {
					if (GameManager.get().isGame(args[1])) {
						Game l = GameManager.get().getGame(args[1]);
						int id = Integer.parseInt(args[2]);
						if (!U.isInt(args[2])) { p.sendMessage(U.pluginPrefix + U.color("&e'%args' &cisnt a number!".replaceAll("%args", id+""))); return true; }
						int n = Integer.parseInt(args[2]);
						if (n<=0) { p.sendMessage(U.pluginPrefix + U.color("&e'%args' &cneeds to be a number above 0!".replaceAll("%args", id+""))); return true; }
						
						
						FileMaps.get().set("Games."+l.getName()+".fragmentSpawns."+id, U.serializeLoc(p.getLocation()));
						FileMaps.save();
						l.getFragmentSpawns().put(id, p.getLocation());
						p.sendMessage(U.pluginPrefix + U.color("&aYour current position has been set as &e'%map' &6#%number fragment spawnpoint&a!".replaceAll("%map", l.getName()).replaceAll("%number", n+"")));
					} else { p.sendMessage(U.pluginPrefix + U.color("&cMap &e'%map' &cdoes not exist!".replaceAll("%map", args[1]))); }
				} break;
			default: p.sendMessage(Messages.cmdDosntExist); return true;
			}
			}
			return true;
		}
		return false;
	}

}
