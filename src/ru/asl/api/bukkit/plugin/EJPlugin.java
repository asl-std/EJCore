package ru.asl.api.bukkit.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ru.asl.api.ejcore.yaml.EJConf;

public abstract class EJPlugin extends JavaPlugin {

	private List<EJConf> cfgs = new ArrayList<>();

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

	public void preInit() {}

	public void disabling() {}

	public void loadConfigurations(EJConf... cfgs) { for (EJConf cfg : cfgs) loadConfiguration(cfg); }
	public void loadListener(Listener listener) {getServer().getPluginManager().registerEvents(listener, this);}

	public void loadConfiguration(EJConf cfg) {
		if (cfgs.contains(cfg)) return;
		else cfgs.add(cfg);
		cfg.reload();
	}

	public void reloadConfigurations() {
		for (EJConf cfg : cfgs)
			cfg.reload();
	}


}
