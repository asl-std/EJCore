package ru.aslcraft.core;

import java.util.LinkedList;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import ru.aslcraft.api.bukkit.entity.EPlayer;
import ru.aslcraft.api.bukkit.material.Material1_12;
import ru.aslcraft.api.bukkit.material.Material1_13;
import ru.aslcraft.api.bukkit.material.interfaze.MaterialAdapter;
import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.api.bukkit.utils.ServerVersion;
import ru.aslcraft.api.ejcore.expension.DataExpansion;
import ru.aslcraft.api.ejcore.plugin.EJPlugin;
import ru.aslcraft.api.ejcore.plugin.Incompatibility;
import ru.aslcraft.api.ejcore.plugin.hook.HookManager;
import ru.aslcraft.api.ejcore.plugin.hook.PAPI;
//import ru.aslcraft.bots.core.discord.BotMain;
import ru.aslcraft.core.commands.CoreCommandHandler;
import ru.aslcraft.core.configs.EConfig;
import ru.aslcraft.core.configs.LangConfig;
import ru.aslcraft.core.listeners.CombatListener;
import ru.aslcraft.core.listeners.EquipListener;
import ru.aslcraft.core.listeners.EquipListener1_13;
import ru.aslcraft.core.listeners.PaneInteractListener;
import ru.aslcraft.core.listeners.PapiListener;
import ru.aslcraft.core.listeners.PlayerListener;
import ru.aslcraft.core.listeners.RegisterEventListener;
import ru.aslcraft.core.listeners.temp.CancelJoinBeforeFullLoading;
import ru.aslcraft.core.managers.ModuleManager;
import ru.aslcraft.core.managers.Tests;
import ru.aslcraft.core.tasks.InitialiseEJPluginsTask;
//import ru.aslcraft.database.core.DBinit;
//import ru.aslcraft.webserver.core.Server;

/**
 * <p>Core class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Core extends EJPlugin {

	/** Constant <code>ANCIITAG</code> */
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
	@Getter private static MaterialAdapter materialAdapter = null;
	private static LinkedList<EJPlugin> plugins;

	//@Getter private static Server webServer;

	private static Core instance = null;
	/**
	 * <p>instance.</p>
	 *
	 * @return a {@link ru.aslcraft.core.Core} object
	 */
	public  static Core instance() { return Core.instance; }


	/*@Override public void onLoad() {
		NBTInjector.inject();
	}*/

	/** {@inheritDoc} */
	@Override public void preInit() {
		init();
		// Just NBT-API init :)
		final NBTItem it = new NBTItem(new ItemStack(Material.IRON_INGOT, 1));
		it.setBoolean("init", true);
	}

	/** {@inheritDoc} */
	@Override public int getPriority() {
		return 0;
	}

	/** {@inheritDoc} */
	@Override public void init() {
		for (final String str : ANCIITAG) EText.send(str);
		final long bef = System.nanoTime();
		instance = this;
		CancelJoinBeforeFullLoading.register();

		resourceId = 38074;
		//EJUpdateChecker.registerEJPlugin(this);

		new Metrics(instance, 2908);
		RegisterEventListener.init(this);

		Core.cfg = new EConfig(getDataFolder() + "/config.yml", this);
		Core.lang = new LangConfig(getDataFolder() + "/lang.yml", this);

		ServerVersion.init(Bukkit.getBukkitVersion(), Bukkit.getName());

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_13))
			Core.materialAdapter = new Material1_13();
		else
			Core.materialAdapter = new Material1_12();

		if (HookManager.tryHookPAPI()) {
			EText.fine("PAPI expansion loaded!");
			new DataExpansion();
			RegisterEventListener.register("papiReload", new PapiListener());
		} else
			EText.warn("I can't create new PAPI expansion because PlaceholderAPI not installed.");

		Tests.start();
		RegisterEventListener.register("playerJoin", new PlayerListener());
		RegisterEventListener.register("paneInteract", new PaneInteractListener());
		RegisterEventListener.register("combatEventCustom", new CombatListener());
		RegisterEventListener.register("equip", new EquipListener());
		RegisterEventListener.register("equip_1_13", new EquipListener1_13(), ServerVersion.isVersionAtMost(ServerVersion.VER_1_13));


		/*new DBinit().init(instance);

		if (Server.createServer()) {
			webServer = new Server();
			webServer.start();
		}
		if(Core.getCfg().getBoolean("discord.ej-discordbot-enabled",false,true)) {
			new Thread(new BotMain()).start();
		}

		if (Core.getCfg().getBoolean("vk.ej-vkbot-enabled", false, true)){
			new Thread(new ru.aslcraft.bots.core.vk.BotMain()).start();
		}*/

		ModuleManager.loadModules(getClassLoader());

		plugins = new LinkedList<>();
		for (final Plugin plugin : Bukkit.getPluginManager().getPlugins())
			if (plugin instanceof EJPlugin && !plugin.getName().equalsIgnoreCase("ejCore"))
				plugins.add((EJPlugin) plugin);

		if (plugins.size() > 0) {
			EText.fine("&aejCore found EJPlugins, wait while all plugins enables.. ");
			new InitialiseEJPluginsTask(plugins).runTaskTimer(this, 10, 10L);
			ModuleManager.enableModules();
		} else {
			ModuleManager.enableModules();
			RegisterEventListener.getListenerManager().register();
			CancelJoinBeforeFullLoading.unregister();
		}

		new CoreCommandHandler().registerHandler();

		final long aft = System.nanoTime();
		EText.fine("&aejCore succesfuly loaded in " + EText.format((aft - bef) / 1e9) + " sec.");
		EText.sendLB();
		Incompatibility.check();
		if (HookManager.isPapiEnabled() && !PAPI.getPreRegister().isEmpty()) {
			PAPI.getPreRegister().values().forEach(PlaceholderExpansion::register);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void disabling() {
		for (final Player p : Bukkit.getOnlinePlayers())
			EPlayer.unregister(p);
		RegisterEventListener.getListenerManager().unregisterAll();
	}

	/**
	 * <p>reloadPlugins.</p>
	 */
	public void reloadPlugins() {
		for (final EJPlugin plugin : plugins)
			plugin.reloadPlugin();
	}

}
