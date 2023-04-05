package org.aslstd.api.ejcore.plugin.hook;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

/**
 * <p>Abstract PAPI class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public abstract class PAPI extends PlaceholderExpansion {

	@Getter private static final ConcurrentMap<String, PlaceholderExpansion> preRegister = new ConcurrentHashMap<>();

	public static final String parseBracketPlaceholders(OfflinePlayer target, String text) {
		if (!HookManager.isPapiEnabled())
			throw new IllegalStateException(
					"This error appear because something tries to use method, "
							+ "but PlaceholderAPI not installed, "
							+ "check below which plugin tried to use this, "
							+ "and send error to author");

		return PlaceholderAPI.setBracketPlaceholders(target, text);
	}

	public static final String parsePlaceholders(OfflinePlayer target, String text) {
		if (!HookManager.isPapiEnabled())
			throw new IllegalStateException(
					"This error appear because something tries to use method, "
							+ "but PlaceholderAPI not installed, "
							+ "check below which plugin tried to use this, "
							+ "and send error to author");

		return PlaceholderAPI.setPlaceholders(target, text);
	}

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
		this.identifier = identifier;
		preRegister.put(identifier, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getAuthor() {
		return "ASL std.";
	}

	/** {@inheritDoc} */
	@Override
	public String getIdentifier() {
		return identifier;
	}

	/** {@inheritDoc} */
	@Override
	public String getRequiredPlugin() {
		return plugin.getName();
	}

	/** {@inheritDoc} */
	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public boolean persist() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	abstract public String onPlaceholderRequest(Player p, String identifier);

}
