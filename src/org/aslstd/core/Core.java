package org.aslstd.core;

import java.util.LinkedHashSet;
import java.util.Set;

import org.aslstd.api.bukkit.command.BasicCommandHandler;
import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.bukkit.redstone.RedstoneParts;
import org.aslstd.api.bukkit.storage.PlayerFileStorage;
import org.aslstd.api.bukkit.utils.ServerVersion;
import org.aslstd.api.ejcore.player.EPlayer;
import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.api.ejcore.plugin.Incompatibility;
import org.aslstd.api.ejcore.plugin.hook.HookManager;
import org.aslstd.api.ejcore.plugin.hook.PAPI;
import org.aslstd.api.ejcore.worker.WorkerService;
import org.aslstd.core.command.CoreCommandHandler;
import org.aslstd.core.config.EConfig;
import org.aslstd.core.config.LangConfig;
import org.aslstd.core.expansion.DataExpansion;
import org.aslstd.core.listener.CombatListener;
import org.aslstd.core.listener.EquipListener;
import org.aslstd.core.listener.PaneInteractListener;
import org.aslstd.core.listener.PlayerListener;
import org.aslstd.core.listener.temp.CancelJoinBeforeFullLoading;
import org.aslstd.core.manager.ModuleManager;
import org.aslstd.core.manager.Tests;
import org.aslstd.core.service.ListenerRegistrator;
import org.aslstd.core.service.ListenerRegistrator.Collector;
import org.aslstd.core.task.InitialiseEJPluginsTask;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

/**
 * <p>Core class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@Accessors(fluent = true)
public class Core extends EJPlugin {

	public static final String[]
			ANCIITAG = {
			            "&4#####################################################################",
			            "&5",
			            "&5   ▄██████▄ &5       ▄█&4  ▄██████▄   ▄██████▄     ▄███████▄    ▄██████▄  ",
			            "&5  ███▀  ▀███&5      ███&4 ███▀  ▀███ ███▀  ▀███   ███▀  ▀███   ███▀  ▀███ ",
			            "&5  ███    █▀ &5      ███&4 ███    █▀  ███    ███   ███    ███   ███    █▀  ",
			            "&5 ▄███▄▄▄    &5      ███&4 ███        ███    ███  ▄███▄▄▄▄███  ▄███▄▄▄     ",
			            "&5▀▀███▀▀     &5      ███&4 ███        ███    ███ ▀▀███▀▀▀▀▀▀  ▀▀███▀▀      ",
			            "&5  ███    █▄ &5      ███&4 ███    █▄  ███    ███ ▀█████████▄    ███    █▄  ",
			            "&5  ███▄  ▄███&5 ██▄ ▄███&4 ███    ███ ███▄  ▄███   ███▀  ▀███   ███▄  ▄███ ",
			            "&5 █████████▀ &5  ▀████▀ &4  ▀██████▀   ▀██████▀    ███    ███    ▀██████▀  ",
			            "&5",
			            "&b         OUR DISCORD CHANNEL:  HTTPS://DISCORD.GG/4NVRjHrcxM         ",
			            "&4#####################################################################", };

	@Getter private static EConfig config;
	@Getter private static LangConfig lang;
	@Getter private static PlayerFileStorage playerDatabase;
	private static Set<EJPlugin> plugins = new LinkedHashSet<>();

	@Getter private static WorkerService workers;

	@Getter private static BasicCommandHandler handler;

	@Getter private static Core instance = null;

	@Override public void preInit() {
		init();
		final NBTItem it = new NBTItem(new ItemStack(Material.IRON_INGOT, 1));
		it.setBoolean("init", true);
	}

	@Override public int getPriority() {
		return 0;
	}

	@Override
	public void onLoad() {
		instance = this;
		Core.config = new EConfig(getDataFolder() + "/config.yml", this);
		final int poolSize = Runtime.getRuntime().availableProcessors();
		workers = new WorkerService(poolSize);
	}

	@Override public void init() {
		final long bef = System.nanoTime();
		CancelJoinBeforeFullLoading.register();

		resourceId = 38074;
		playerDatabase = PlayerFileStorage.createDatabase(this);

		new Metrics(instance, 2908);

		RedstoneParts.init();

		Core.lang = new LangConfig(getDataFolder() + "/lang.yml", this);

		if (!config.LESS_CONSOLE)
			for (final String str : ANCIITAG) EText.send(str);

		ServerVersion.init(Bukkit.getBukkitVersion(), Bukkit.getName());

		if (HookManager.tryHookPAPI()) {
			EText.fine("PAPI expansion loaded!");
			new DataExpansion();
		} else
			EText.warn("I can't create new PAPI expansion because PlaceholderAPI not installed.");

		Tests.start();
		Collector.forPlugin(this).collect(new PlayerListener(), new PaneInteractListener(), new CombatListener(), new EquipListener()).push();

		handler = new CoreCommandHandler().registerHandler();

		ModuleManager.loadModules(getClassLoader());

		for (final Plugin plugin : Bukkit.getPluginManager().getPlugins())
			if (plugin instanceof EJPlugin && !plugin.getName().equalsIgnoreCase("ejCore"))
				plugins.add((EJPlugin) plugin);

		if (plugins.size() > 0) {
			EText.fine("&aejCore found EJPlugins, wait while all plugins enables.. ");
			new InitialiseEJPluginsTask(plugins).runTaskTimer(this, 0, 40L);
		} else {
			ModuleManager.enableModules();
			ListenerRegistrator.register();
			CancelJoinBeforeFullLoading.unregister();
		}

		EText.fine("&aejCore succesfuly loaded in " + EText.format((System.nanoTime() - bef) / 1e9) + " sec.");
		EText.sendLB();
		Incompatibility.check();
		if (HookManager.isPapiEnabled() && !PAPI.getPreRegister().isEmpty())
			PAPI.getPreRegister().values().forEach(PlaceholderExpansion::register);
	}

	@Override
	public void disabling() {
		EPlayer.stash().clear();
		ListenerRegistrator.unregister();
		workers.shutdown();
	}

	public void reloadPlugins() {
		plugins.forEach(EJPlugin::reloadPlugin);
	}

}
