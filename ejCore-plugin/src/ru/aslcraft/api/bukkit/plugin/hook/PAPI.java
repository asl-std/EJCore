package ru.aslcraft.api.bukkit.plugin.hook;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

/**
 * <p>Abstract PAPI class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public abstract class PAPI extends PlaceholderExpansion {

	private String		identifier;

	private JavaPlugin	plugin;

	/**
	 * <p>Constructor for PAPI.</p>
	 *
	 * @param plugin a {@link org.bukkit.plugin.java.JavaPlugin} object
	 * @param identifier a {@link java.lang.String} object
	 */
	public PAPI(JavaPlugin plugin, String identifier) {
		this.plugin = plugin;
		this.identifier = "ejc_" + identifier;
		register();
	}

	/** {@inheritDoc} */
	@Override
	public String getAuthor() {
		return "ASL";
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentifier() {
		return identifier;
	}

	/** {@inheritDoc} */
	@Override
	public String getPlugin() {
		return "[EJC] > " + plugin.getName();
	}

	/** {@inheritDoc} */
	@Override
	public String getVersion() {
		return "1.0";
	}

	/** {@inheritDoc} */
	@Override
	abstract public String onPlaceholderRequest(Player p, String identifier);

}
