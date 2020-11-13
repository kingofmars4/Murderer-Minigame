package me.kingofmars4.murderer.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Game {
	
	private final String name;
	private HashMap<Integer, Location> spawns = new HashMap<Integer, Location>();
	private HashMap<Integer, Location> fragmentSpawns = new HashMap<Integer, Location>();
	private Location lobbySpawn;
	private final List<Player> players = new ArrayList<Player>();
	
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
}
