package me.kingofmars4.murderer.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.kingofmars4.murderer.utils.U;

public class SignRewriting implements Listener {
	
	@EventHandler
	public void writeSign(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("murderer")) {
			if (e.getLine(1).equalsIgnoreCase("create")) {
				if (e.getPlayer().hasPermission("murderer.createsign")) {
					e.setLine(0, U.color("&4&l[Murderer]")); 
					e.setLine(1, U.color("&2&lOPEN"));
					e.setLine(2, U.color("&8Right click to join!"));
					e.setLine(3, U.color("&1[0/10]"));
				}
			}
		}
	}
}
