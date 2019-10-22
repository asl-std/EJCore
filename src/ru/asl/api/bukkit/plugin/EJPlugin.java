package ru.asl.api.bukkit.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class EJPlugin extends JavaPlugin {

	protected boolean showLoadMessage;

	@Override
	public void onEnable() {
		super.onEnable();

		preInit();

		init();
	}

	@Override
	public void onDisable() {
		super.onDisable();

		disabling();
	}

	public abstract void init();

	public void preInit() {}

	public void disabling() {}

}
