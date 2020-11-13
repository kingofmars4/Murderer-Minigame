package me.kingofmars4.murderer.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.kingofmars4.murderer.files.FileMaps;
import me.kingofmars4.murderer.utils.Messages;
import me.kingofmars4.murderer.utils.U;

public class GameManager {
	
	private static GameManager mm;
	private GameManager() {}
	private final List<Game> Games = new ArrayList<Game>();
	
	public static GameManager get() {
		if (mm == null)
            mm = new GameManager();
        return mm;
	}
	
	private static String key;
	public void loadCache() {
		if (!FileMaps.get().isConfigurationSection("Maps")) { return; }
		for (String k : FileMaps.get().getConfigurationSection("Maps").getKeys(true)) {
			if (!k.contains(".")) {
				key=k;
				createGame(key, U.deserializeLoc(FileMaps.get().getString("Maps."+key+".lobbySpawn")));
				Bukkit.broadcastMessage("Nome: "+key+"    Loc: "+ FileMaps.get().getString("Maps."+key+".lobbySpawn"));
				
				// LOAD SPAWNS
				if (FileMaps.get().contains("Maps."+key+".spawns")) { for (String id : FileMaps.get().getConfigurationSection("Maps."+key+".spawns").getKeys(true)) { if (U.isInt(id)) {
							getGame(key).getSpawns().put(Integer.parseInt(id), U.deserializeLoc(FileMaps.get().getString("Maps."+key+".spawns."+id)));
							Bukkit.broadcastMessage("SPAWNID: "+id + "    LOC: "+FileMaps.get().getString("Maps."+key+".spawns."+id));
							Bukkit.broadcastMessage("--------------------");
				}	}	}
			}
		}
	}
	
	public void createGame(String name, Location l) {
		this.Games.add(new Game(name, l));
		FileMaps.get().set("Maps."+name+".lobbySpawn", U.serializeLoc(l));
		FileMaps.save();
	}
	
	public void deleteGame(String name) {
		Game l = getGame(name);
		
		FileMaps.get().set("Maps."+l.getName(), null);
		FileMaps.save();
		this.Games.remove(l);
	}
	
	public Game getGame (String name) {
		for (Game Games : this.Games) {
			if (name.equalsIgnoreCase(Games.getName())) {
				return Games;
			}
		}
		return null;
	}
	
	public boolean isGame (String name) {
		for (Game Games : this.Games) {
			if (name.equalsIgnoreCase(Games.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInGame(Player p) {
        for (Game a : this.Games) {
            if (a.getPlayers().contains(p))
                return true;
        }
        return false;
    }
	
	
	private final Map<UUID, Location> locs = new HashMap<UUID, Location>();
	private final Map<UUID, ItemStack[]> inv = new HashMap<UUID, ItemStack[]>();
    private final Map<UUID, ItemStack[]> armor = new HashMap<UUID, ItemStack[]>();
    public void addPlayer(Player p, Game m) {
    	ItemStack leaveGame = U.createItemStack(Material.REDSTONE_TORCH_ON, "&cLeave Games");
        
        m.getPlayers().add(p);
        
        inv.put(p.getUniqueId(), p.getInventory().getContents());
        armor.put(p.getUniqueId(), p.getInventory().getArmorContents());
 
        p.getInventory().setArmorContents(null);
        p.getInventory().clear();
        
        locs.put(p.getUniqueId(), p.getLocation());
        p.teleport(m.getLobbySpawn());
		p.getInventory().setItem(8, leaveGame);
		p.sendMessage(Messages.pluginPrefix+U.color("&aYou have joined the game!"));
    }
    
    public void removePlayer(Player p) {
        Game a = null;

        // Searches each arena for the player
        for (Game l : this.Games) {
            if (l.getPlayers().contains(p))
                a = l;
        }

        if (a == null) {
            p.sendMessage("Invalid operation!");
            return;
        }

        a.getPlayers().remove(p);

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        
        p.getInventory().setContents(inv.get(p.getUniqueId()));
        p.getInventory().setArmorContents(armor.get(p.getUniqueId()));
        
        inv.remove(p.getUniqueId());
        armor.remove(p.getUniqueId());

        p.teleport(locs.get(p.getUniqueId()));
        locs.remove(p.getUniqueId());
        p.setFireTicks(0);
        p.updateInventory();
    }
    
    public void forceShutdown()  {
    	for (Game l : Games) {
    		for (Player p : l.getPlayers()) {
    			p.sendMessage(Messages.pluginPrefix + U.color("&c&lSHUTING DOWN ALL CURRENT ONGOING GAMES!!"));
    			removePlayer(p);
    		}
    	}
    }
}
