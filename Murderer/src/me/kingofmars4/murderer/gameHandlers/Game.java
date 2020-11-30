package me.kingofmars4.murderer.gameHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;
import me.kingofmars4.murderer.Main;
import me.kingofmars4.murderer.utils.Messages;
import me.kingofmars4.murderer.utils.U;

public class Game implements Listener {
	
	
	
	/**************************************************************************
     * 								VARIABLES
     *************************************************************************/
	private final String name;
	
	private HashMap<Integer, Location> spawns = new HashMap<Integer, Location>();
	private HashMap<Integer, Location> fragmentSpawns = new HashMap<Integer, Location>();
	private final List<Player> players = new ArrayList<Player>();
	
	private BukkitTask task;
	private Location lobbySpawn;
	
	private boolean started = false;
	private boolean counting = false;
	
	/**************************************************************************
     * 								GAME ITEMS
     *************************************************************************/
	private final ItemStack murdererWeapon = U.createItemStack(Material.IRON_SWORD, U.color("&cMurderer Knife"));
	private final ItemStack bystanderWeapon = U.createItemStack(Material.BOW, U.color("&9Revolver 38"));
	private final ItemStack gem = U.createItemStack(Material.EMERALD, U.color("&aGem"));
	
	
	
	
	/**************************************************************************
     * 								GETTERS AND SETTERS
     *************************************************************************/
	public Game (String name, Location lobbySpawn) {
		this.name=name;
		this.lobbySpawn=lobbySpawn;
	}
	
	public String getName() {
		return this.name;
	}
	
	public HashMap<Integer, Location> getSpawns() {
		return this.spawns;
	}
	
	public HashMap<Integer, Location> getFragmentSpawns() {
		return this.fragmentSpawns;
	}

	public Location getLobbySpawn() {
		return this.lobbySpawn;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public boolean started() {
		return this.started;
	}
	
	
	
	
	
	/**************************************************************************
     * 								ADD & REMOVE
     *************************************************************************/
	private final Map<UUID, Location> locs = new HashMap<UUID, Location>();
	private final Map<UUID, ItemStack[]> inv = new HashMap<UUID, ItemStack[]>();
    private final Map<UUID, ItemStack[]> armor = new HashMap<UUID, ItemStack[]>();
    
    public void addPlayer(Player p) {
    	ItemStack leaveLobby = U.createItemStack(Material.REDSTONE_TORCH_ON, "&cLeave Lobby");
        
    	getPlayers().add(p);
    	lobbyHandler();
    	
    	p.sendMessage(Messages.pluginPrefix+U.color("&aYou have joined the waiting lobby!"));
    	broadcast("&e%p &ahas joined the lobby!".replace("%p", p.getName()));
    	if (!counting) { broadcast("&c[!] &e%n &6player(s) left to start countdown!".replace("%n", 4-getPlayers().size()+"")); }
        
        inv.put(p.getUniqueId(), p.getInventory().getContents());
        armor.put(p.getUniqueId(), p.getInventory().getArmorContents());
 
        p.getInventory().setArmorContents(null);
        p.getInventory().clear();
        
        locs.put(p.getUniqueId(), p.getLocation());
        p.teleport(getLobbySpawn());
		p.getInventory().setItem(8, leaveLobby);
    }
    
    public void removePlayer(Player p) {
    	
        getPlayers().remove(p);
        lobbyHandler();
        
        broadcast("&e%p &chas left the lobby!".replace("%p", p.getName()));
    	if (!counting) { broadcast("&c[!] &e%n &6player(s) left to start countdown!".replace("%n", 4-getPlayers().size()+"")); }

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
    
    
    
    
    /**************************************************************************
     * 								LOBBY HANDLER
     *************************************************************************/
    private void lobbyHandler() {
    	if (getPlayers().size() >= 3) {
    		counting = true;
    	} else {
    		counting = false;
    	}
    }
    
    
    
    
    
    
    /**************************************************************************
     * 								UTIL METHODS
     *************************************************************************/
    public void broadcast(String message) {
    	for (Player p : getPlayers()) {
    		p.sendMessage(U.color(message));
    	}
    }
    
    
    
    
    /**************************************************************************
     * 								START AND END
     *************************************************************************/
    public void start() {
    	if (started()) { throw new IllegalStateException("start() called while game has alredy started!"); }
    	
    	for (Player p : getPlayers()) {
            p.closeInventory();

            for(PotionEffect e : p.getActivePotionEffects()) { p.removePotionEffect(e.getType()); }

            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);

            p.getInventory().clear();
            
        }

    	task = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), this::tick, 1, 20);
    	started = true;
    }
    
    public void end() {
    	
    	for (Player p : getPlayers()) {
			removePlayer(p);
		}
    	
    	if (task != null) {
    		task.cancel();
    		task = null;
    	}
    }
    
    
    
    
    /**************************************************************************
     * 									TICK
     *************************************************************************/
    private void tick() {
    	while(counting == true) {
    		broadcast("TA A CONTAR");
    	}
	}
    
    
    
    
    
    /**************************************************************************
     * 								LISTENERS
     *************************************************************************/
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e)
    {
        if (GameManager.get().isInGame(e.getPlayer())) {
        	e.setCancelled(true);
        }
    }
}
