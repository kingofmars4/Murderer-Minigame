package me.kingofmars4.murderer.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.kingofmars4.murderer.handlers.GameManager;
import me.kingofmars4.murderer.utils.Messages;
import me.kingofmars4.murderer.utils.U;

public class LobbyItems implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void rightClick(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player p = e.getPlayer();
			if (p.getItemInHand().getType().equals(Material.REDSTONE_TORCH_ON)) {
				if (p.getItemInHand().getItemMeta().getDisplayName().equals(U.color("&cLeave Lobby"))) {
					if (GameManager.get().isInGame(p)) {
						GameManager.get().removePlayer(p);
						p.sendMessage(Messages.pluginPrefix+U.color("&cYou have left the game!"));
					} else { p.sendMessage(Messages.pluginPrefix+U.color("&cYou must be in a lobby to use this item!")); }
				}
			}
		}
	}
}
