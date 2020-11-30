package me.kingofmars4.murderer.gameHandlers;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import me.kingofmars4.murderer.files.FileMaps;
import me.kingofmars4.murderer.utils.Messages;
import me.kingofmars4.murderer.utils.U;

public class GameManager {
	
	private static GameManager gm;
	private GameManager() {}
	private final List<Game> Games = new ArrayList<Game>();
	
	public static GameManager get() {
		if (gm == null)
            gm = new GameManager();
        return gm;
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
	
	public Game getPlayerGame(Player p) {
		for (Game a : this.Games) {
            if (a.getPlayers().contains(p))
                return a;
        }
        return null;
	}
    
    public void forceShutdown()  {
    	Bukkit.broadcastMessage(U.pluginPrefix + U.color("&c&lSHUTING DOWN ALL CURRENT ONGOING GAMES!!"));
    	for (Game l : Games) {
    		l.end();
    	}
    	
    }
}
