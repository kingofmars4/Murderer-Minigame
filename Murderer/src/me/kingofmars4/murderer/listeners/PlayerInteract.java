package me.kingofmars4.murderer.listeners;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.kingofmars4.murderer.utils.U;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				Player p = e.getPlayer();
				
				if (sign.getLine(1).equalsIgnoreCase(U.color("&2&lOPEN"))) {
					p.sendMessage("clicaste");
				}
			}
		}
	}

}
