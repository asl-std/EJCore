package ru.aslcraft.core.configs;

import ru.aslcraft.api.bukkit.yaml.EJConf;
import ru.aslcraft.api.ejcore.plugin.EJPlugin;

/**
 * <p>EConfig class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class EConfig extends EJConf {

	public boolean	ONE_HP_BAR, ENABLE_CONSOLE_COLORS;
	public int		HEALTH_PER_BAR, UPDATE_PERIOD;
	public boolean	DEBUG_RUNNING, ENABLE_ATTACK_COOLDOWN, CHECK_UPDATE, PLAYER_ATTRIBUTES_ENABLED;

	/**
	 * <p>Constructor for EConfig.</p>
	 *
	 * @param fileName a {@link java.lang.String} object
	 * @param plugin a {@link ru.aslcraft.api.ejcore.plugin.EJPlugin} object
	 */
	public EConfig(String fileName, EJPlugin plugin) {
		super(fileName, plugin);
	}

	/** {@inheritDoc} */
	@Override
	public void loadConfig() {
		ENABLE_CONSOLE_COLORS = this.getBoolean("enable-console-colors", true, true);
		ONE_HP_BAR = this.getBoolean("health-bar.fix-to-one-line", false, true);
		HEALTH_PER_BAR = this.getInt("health-bar.health-per-bar", 20, true);

		DEBUG_RUNNING = this.getBoolean("debug.enable-debug", true, true);
		ENABLE_ATTACK_COOLDOWN = this.getBoolean("enable-attack-cooldown", true, true);
		CHECK_UPDATE = this.getBoolean("check-updates", true, true);
		UPDATE_PERIOD = this.getInt("check-period", 28800, true);
		PLAYER_ATTRIBUTES_ENABLED = this.getBoolean("modules.player-attributes-enabled", false, true);
	}

}
