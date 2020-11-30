package me.kingofmars4.murderer.listeners;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.kingofmars4.murderer.gameHandlers.Game;
import me.kingofmars4.murderer.gameHandlers.GameManager;
import me.kingofmars4.murderer.utils.Messages;
import me.kingofmars4.murderer.utils.U;

public class UseSign implements Listener {
	
	public int counter = 1;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				Player p = e.getPlayer();
				if (sign.getLine(1).equalsIgnoreCase(U.color("&a&lOPEN"))) {
					String mapName =  sign.getLine(2).replaceAll("§9§l", "");
					
					if (GameManager.get().isGame(mapName)) { 
						Game map = GameManager.get().getGame(mapName);
						
						if (!map.getSpawns().isEmpty()) {
						
							map.addPlayer(p);
							
						} else { p.sendMessage(Messages.pluginPrefix+U.color("&cThis map does not have any spawnpoints set!")); }
					} else { p.sendMessage(Messages.pluginPrefix+U.color("&cThis map no longer exist!")); }
				}
			}
		}
	}
}
