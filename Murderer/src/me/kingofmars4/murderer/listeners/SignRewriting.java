package me.kingofmars4.murderer.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.kingofmars4.murderer.gameHandlers.Game;
import me.kingofmars4.murderer.gameHandlers.GameManager;
import me.kingofmars4.murderer.utils.Messages;
import me.kingofmars4.murderer.utils.U;

public class SignRewriting implements Listener {

	@EventHandler
	public void writeSign(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("murderer")) {
			Player p = e.getPlayer();
			if (e.getLine(1).equalsIgnoreCase("create")) {
				if (p.hasPermission("murderer.createsign")) {
					if (GameManager.get().isGame(e.getLine(2))) {
						Game l = GameManager.get().getGame(e.getLine(2));
						
						e.setLine(0, U.color("&4[Murderer]"));
						e.setLine(1, U.color("&2&lOPEN"));
						e.setLine(2, U.color("&9&l")+l.getName());
						e.setLine(3, U.color("&1Right click to join"));
							
						p.sendMessage(Messages.pluginPrefix + U.color("&aJoin sign succesefully created for map &e'%map'&a!".replaceAll("%map", l.getName())));
							
					} else { p.sendMessage(Messages.pluginPrefix + U.color("&e'%map' &cis not a valid map!".replaceAll("%map", e.getLine(2)))); e.getBlock().breakNaturally();}
				}
			}
		}
	}
}
