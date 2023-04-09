package org.aslstd.core;

import java.util.LinkedHashSet;
import java.util.Set;

import org.aslstd.api.bukkit.command.BasicCommandHandler;
import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.bukkit.redstone.RedstoneParts;
import org.aslstd.api.bukkit.utils.ServerVersion;
import org.aslstd.api.bukkit.yaml.player.PlayerDatabase;
import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.api.ejcore.plugin.Incompatibility;
import org.aslstd.api.ejcore.plugin.hook.HookManager;
import org.aslstd.api.ejcore.plugin.hook.PAPI;
import org.aslstd.api.ejcore.worker.WorkerService;
import org.aslstd.core.commands.CoreCommandHandler;
import org.aslstd.core.configs.EConfig;
import org.aslstd.core.configs.LangConfig;
import org.aslstd.core.expansion.DataExpansion;
import org.aslstd.core.listeners.CombatListener;
import org.aslstd.core.listeners.EquipListener;
import org.aslstd.core.listeners.EquipListener1_13;
import org.aslstd.core.listeners.PaneInteractListener;
import org.aslstd.core.listeners.PlayerListener;
import org.aslstd.core.listeners.RegisterEventListener;
import org.aslstd.core.listeners.temp.CancelJoinBeforeFullLoading;
import org.aslstd.core.managers.ModuleManager;
import org.aslstd.core.managers.Tests;
import org.aslstd.core.tasks.InitialiseEJPluginsTask;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

/**
 * <p>Core class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Core extends EJPlugin {

	public static final String[] ANCIITAG = {
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

	@Getter private static EConfig cfg;
	@Getter private static LangConfig lang;
	@Getter private static PlayerDatabase playerDatabase;
	private static Set<EJPlugin> plugins = new LinkedHashSet<>();

	@Getter private static WorkerService workers;

	@Getter private static BasicCommandHandler handler;

	private static Core instance = null;
	public  static Core instance() { return Core.instance; }

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
		Core.cfg = new EConfig(getDataFolder() + "/config.yml", this);
		final int poolSize = Runtime.getRuntime().availableProcessors();
		workers = new WorkerService(poolSize);
	}

	@Override public void init() {
		final long bef = System.nanoTime();
		CancelJoinBeforeFullLoading.register();

		resourceId = 38074;
		playerDatabase = PlayerDatabase.createDatabase(this);

		new Metrics(instance, 2908);
		RegisterEventListener.init(this);
		RedstoneParts.init();

		Core.lang = new LangConfig(getDataFolder() + "/lang.yml", this);

		if (!cfg.LESS_CONSOLE)
			for (final String str : ANCIITAG) EText.send(str);

		ServerVersion.init(Bukkit.getBukkitVersion(), Bukkit.getName());

		if (HookManager.tryHookPAPI()) {
			EText.fine("PAPI expansion loaded!");
			new DataExpansion();
		} else
			EText.warn("I can't create new PAPI expansion because PlaceholderAPI not installed.");

		Tests.start();
		RegisterEventListener.register("playerJoin", new PlayerListener());
		RegisterEventListener.register("paneInteract", new PaneInteractListener());
		RegisterEventListener.register("combatEventCustom", new CombatListener());
		RegisterEventListener.register("equip", new EquipListener());
		RegisterEventListener.register("equip_1_13", new EquipListener1_13(), ServerVersion.isVersionAtMost(ServerVersion.VER_1_13));

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
			RegisterEventListener.getListenerManager().register();
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
		Bukkit.getOnlinePlayers().forEach(EPlayer::unregister);
		RegisterEventListener.getListenerManager().unregisterAll();
		workers.shutdown();
	}

	public void reloadPlugins() {
		plugins.forEach(EJPlugin::reloadPlugin);
	}

}