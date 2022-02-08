package ru.asl.api.bukkit.plugin.hook;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public abstract class PAPI extends PlaceholderExpansion {

	private String		identifier;

	private JavaPlugin	plugin;

	public PAPI(JavaPlugin plugin, String identifier) {
		this.plugin = plugin;
		this.identifier = "ejc_" + identifier;
		this.register();
	}

	@Override
	public String getAuthor() {
		return "ASLTeam";
	}

	@Override
	public String getIdentifier() {
		return this.identifier;
	}

	@Override
	public String getPlugin() {
		return "[EJCore] >" + this.plugin.getName();
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	abstract public String onPlaceholderRequest(Player p, String identifier);

}
