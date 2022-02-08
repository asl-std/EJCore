package ru.asl.api.bukkit.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.Setter;
import ru.asl.api.ejcore.yaml.EJConf;

public abstract class EJPlugin extends JavaPlugin {

	private List<EJConf> cfgs = new ArrayList<>();

	@Getter protected int resourceId = -1;

	@Getter @Setter protected int build;
	@Getter @Setter protected int latestBuild;

	@Getter @Setter protected String latestVersion;

	/**
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

	@Override
	public void onDisable() {
		super.onDisable();

		HandlerList.unregisterAll(this);
		disabling();
	}

	public abstract void init();

	public int getPriority() { return 10; }

	public void preInit() {}

	public void disabling() {}

	public void reloadPlugin() {}

	public void loadConfigurations(EJConf... cfgs) { for (final EJConf cfg : cfgs) loadConfiguration(cfg); }
	public void loadListener(Listener listener) {getServer().getPluginManager().registerEvents(listener, this);}

	public void loadConfiguration(EJConf cfg) {
		if (cfgs.contains(cfg)) return;
		else cfgs.add(cfg);
		cfg.reload();
	}

	public void reloadConfigurations() {
		for (final EJConf cfg : cfgs)
			cfg.reload();
	}


}
