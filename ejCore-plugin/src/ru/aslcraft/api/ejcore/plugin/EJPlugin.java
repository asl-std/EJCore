package ru.aslcraft.api.ejcore.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.Setter;
import ru.aslcraft.api.bukkit.yaml.EJConf;

/**
 * <p>Abstract EJPlugin class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public abstract class EJPlugin extends JavaPlugin {

	private List<EJConf> cfgs = new ArrayList<>();

	@Getter protected int resourceId = -1;

	@Getter @Setter protected int build;
	@Getter @Setter protected int latestBuild;

	@Getter @Setter protected String latestVersion;

	/**
	 * {@inheritDoc}
	 *
	 * EJPlugin::preInit() used for initialise features without using ListenerLoader and ModuleLoader
	 *
	 * EJPlugin::init() will be started automatically from Core when all EJPlugin's will be enabled
	 */
	@Override
	public void onEnable() {
		super.onEnable();

		preInit();

		reloadConfigurations();
	}

	/** {@inheritDoc} */
	@Override
	public void onDisable() {
		super.onDisable();

		HandlerList.unregisterAll(this);
		disabling();
	}

	/**
	 * <p>init.</p>
	 */
	public abstract void init();

	/**
	 * <p>getPriority.</p>
	 *
	 * @return a int
	 */
	public int getPriority() { return 10; }

	/**
	 * <p>preInit.</p>
	 */
	public void preInit() {}

	/**
	 * <p>disabling.</p>
	 */
	public void disabling() {}

	/**
	 * <p>reloadPlugin.</p>
	 */
	public void reloadPlugin() {}

	/**
	 * <p>loadConfigurations.</p>
	 *
	 * @param cfgs a {@link ru.aslcraft.api.ejcore.yaml.EJConf} object
	 */
	public void loadConfigurations(EJConf... cfgs) { for (final EJConf cfg : cfgs) loadConfiguration(cfg); }
	/**
	 * <p>loadListener.</p>
	 *
	 * @param listener a {@link org.bukkit.event.Listener} object
	 */
	@Deprecated
	public void loadListener(Listener listener) { getServer().getPluginManager().registerEvents(listener, this); }

	/**
	 * <p>loadConfiguration.</p>
	 *
	 * @param cfg a {@link ru.aslcraft.api.ejcore.yaml.EJConf} object
	 */
	public void loadConfiguration(EJConf cfg) {
		if (cfgs.contains(cfg)) return;
		else cfgs.add(cfg);
		cfg.reload();
	}

	/**
	 * <p>reloadConfigurations.</p>
	 */
	public void reloadConfigurations() {
		for (final EJConf cfg : cfgs)
			cfg.reload();
	}


}
