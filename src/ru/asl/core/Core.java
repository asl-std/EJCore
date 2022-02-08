package ru.asl.core;

import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import lombok.Getter;
import ru.asl.api.bukkit.item.Material1_12;
import ru.asl.api.bukkit.item.Material1_13;
import ru.asl.api.bukkit.item.interfaze.MaterialAdapter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.api.bukkit.plugin.hook.HookManager;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.reflection.utils.RefUtils;
import ru.asl.api.ejcore.utils.ServerVersion;
import ru.asl.core.commands.CoreCommandHandler;
import ru.asl.core.configs.EConfig;
import ru.asl.core.configs.LangConfig;
import ru.asl.core.events.CombatListener;
import ru.asl.core.events.EquipListener;
import ru.asl.core.events.EquipListener1_13;
import ru.asl.core.events.PaneInteractListener;
import ru.asl.core.events.PlayerListener;
import ru.asl.core.events.temp.CancelJoinBeforeFullLoading;
import ru.asl.core.managers.ListenerManager;
import ru.asl.core.managers.ModuleManager;
import ru.asl.core.managers.Tests;
import ru.asl.core.metrics.Metrics;
import ru.asl.core.tasks.InitialiseEJPluginsTask;
import ru.asl.modules.playerstats.basic.StatManager;

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
			"&b         OUR DISCORD CHANNEL:  HTTPS://DISCORD.GG/s9aqQSMqRy         ",
			"&4#####################################################################", };

	@Getter private static EConfig cfg;
	@Getter private static LangConfig lang;
	@Getter private static ListenerManager eventLoader = null;
	@Getter private static MaterialAdapter materialAdapter = null;
	@Getter private static RefUtils reflections = null;
	@Getter private static StatManager stats = null;

	private static Core instance = null;
	public  static Core instance() { return Core.instance; }

	@Override public void preInit() { init(); }

	@Override public int getPriority() { return 0; }

	@Override public void init() {
		for (final String str : ANCIITAG) EText.send(str);
		final long bef = System.nanoTime();
		instance = this;
		CancelJoinBeforeFullLoading.register();

		resourceId = 38074;
		//EJUpdateChecker.registerEJPlugin(this);

		new Metrics(instance, 2908);

		Core.reflections = new RefUtils();
		Core.eventLoader = new ListenerManager(this);

		Core.cfg = new EConfig(getDataFolder() + "/config.yml", this);
		Core.lang = new LangConfig(getDataFolder() + "/lang.yml", this);

		ServerVersion.init(Bukkit.getBukkitVersion(), Bukkit.getName());

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_13))
			Core.materialAdapter = new Material1_13();
		else
			Core.materialAdapter = new Material1_12();

		HookManager.tryHookPAPI();

		Tests.start();

		Core.getEventLoader().addPreReg("playerJoin", new PlayerListener());
		Core.getEventLoader().addPreReg("paneInteract", new PaneInteractListener());
		Core.getEventLoader().addPreReg("combatEventCustom", new CombatListener());
		Core.getEventLoader().addPreReg("equip", new EquipListener());
		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_13))
			Core.getEventLoader().addPreReg("equip", new EquipListener1_13());

		ModuleManager.loadModules(getClassLoader());

		final LinkedList<EJPlugin> plugins = new LinkedList<>();
		for (final Plugin plugin : Bukkit.getPluginManager().getPlugins())
			if (plugin instanceof EJPlugin && !plugin.getName().equalsIgnoreCase("ejCore"))
				plugins.add((EJPlugin) plugin);

		if (plugins.size() > 0) {
			EText.fine("&aejCore found EJPlugins, wait while all plugins enables.. ");
			new InitialiseEJPluginsTask(plugins).runTaskTimer(this, 10, 10L);
			ModuleManager.enableModules();
		} else {
			ModuleManager.enableModules();
			Core.eventLoader.register();
		}
		registerStatManager();

		new CoreCommandHandler().registerHandler();

		final long aft = System.nanoTime();
		EText.fine("&aejCore succesfuly loaded in " + EText.format((aft - bef) / 1e9) + " sec.");
		EText.sendLB();
	}

	@Override
	public void disabling() {
		for (final Player p : Bukkit.getOnlinePlayers())
			EPlayer.removeRegistered(p);
		Core.eventLoader.unregisterAll();
	}

	public void registerStatManager() {
		if (stats != null) return;

		if (cfg.PLAYER_STATS_ENABLED) {
			stats = new StatManager();
			stats.register();
		}
	}

	@Override
	public void reloadPlugin() {}


	public void unregisterListeners() {
		HandlerList.unregisterAll(getEventLoader().getMainPlugin());
	}



}
