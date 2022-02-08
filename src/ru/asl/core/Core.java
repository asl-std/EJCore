package ru.asl.core;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import lombok.Getter;
import ru.asl.api.bukkit.item.Material1_12;
import ru.asl.api.bukkit.item.Material1_13;
import ru.asl.api.bukkit.item.interfaze.MaterialAdapter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.api.bukkit.plugin.hook.HookManager;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.items.ItemStackUtil;
import ru.asl.api.ejcore.module.EJAddon;
import ru.asl.api.ejcore.module.EJModule;
import ru.asl.api.ejcore.reflection.utils.RefUtils;
import ru.asl.api.ejcore.utils.ServerVersion;
import ru.asl.api.ejcore.value.DoubleSettings;
import ru.asl.core.commands.CoreCommandHandler;
import ru.asl.core.configs.EConfig;
import ru.asl.core.configs.LangConfig;
import ru.asl.core.events.CombatListener;
import ru.asl.core.events.EquipListener;
import ru.asl.core.events.EquipListener1_13;
import ru.asl.core.events.PaneInteractListener;
import ru.asl.core.events.PlayerListener;
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
	@Getter private static ListenerLoader eventLoader = null;
	@Getter private static MaterialAdapter materialAdapter = null;
	@Getter private static RefUtils reglections = null;
	@Getter private static StatManager stats = null;

	private static Core instance = null;
	public  static Core instance() { return Core.instance; }

	private static final HashMap<String, EJModule> eJModules = new HashMap<>();
	private static final ArrayList<String> loadedJars = new ArrayList<>();

	@Override public void preInit() { init(); }

	@Override public int getPriority() { return 0; }

	@Override public void init() {
		for (final String str : ANCIITAG)
			EText.send(str);
		final long bef = System.nanoTime();

		instance = this;

		resourceId = 38074;
		//EJUpdateChecker.registerEJPlugin(this);

		new Metrics(instance, 2908);

		reglections = new RefUtils();
		Core.eventLoader = new ListenerLoader(this);

		Core.cfg = new EConfig(getDataFolder() + "/config.yml", this);
		Core.lang = new LangConfig(getDataFolder() + "/lang.yml", this);

		ServerVersion.init(Bukkit.getBukkitVersion(), Bukkit.getName());

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_13))
			Core.materialAdapter = new Material1_13();
		else
			Core.materialAdapter = new Material1_12();

		HookManager.tryHookPAPI();

		testStringBuffer();
		testSettings();

		Core.getEventLoader().addPreReg("playerJoin", new PlayerListener());
		Core.getEventLoader().addPreReg("paneInteract", new PaneInteractListener());
		Core.getEventLoader().addPreReg("combatEventCustom", new CombatListener());
		Core.getEventLoader().addPreReg("equip", new EquipListener());
		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_13))
			Core.getEventLoader().addPreReg("equip", new EquipListener1_13());

		for (final EJModule module : eJModules.values()) {
			module.loadModule();
			EText.fine("&aModule: &5'" + module.getModuleName() + "' &aloaded, version: &5" + module.getModuleVersion());
		}

		final LinkedList<EJPlugin> plugins = new LinkedList<>();
		for (final Plugin plugin : Bukkit.getPluginManager().getPlugins())
			if (plugin instanceof EJPlugin && !plugin.getName().equalsIgnoreCase("ejCore"))
				plugins.add((EJPlugin) plugin);

		if (plugins.size() > 0) {
			EText.fine("&aejCore found EJPlugins, wait while all plugins enables.. ");
			new InitialiseEJPluginsTask(plugins).runTaskTimer(this, 10, 10L);
			loadModules();
		} else {
			loadModules();
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
	public void reloadPlugin() {

	}

	public void testStringBuffer() {
		EText.debug("ItemHashConverter System: Checking..♥♦♣♠");
		final List<String> hashes = Arrays.asList(
				"IRON_SWORD:1:0♦&4Уровень: 1◘&4Крит-Шанс: +3.0◘&5&m--===[&6&l  Аттрибуты &5&m]===--&3Урон: +6.0-8.0♥&9Iron Sword");

		for (final String hash : hashes) {
			final ItemStack stack = ItemStackUtil.toStack(hash);
			if (!ItemStackUtil.toString(stack).equalsIgnoreCase(
					"IRON_SWORD:1:0♥&9Iron Sword♦&4Уровень: 1◘&4Крит-Шанс: +3.0◘&5&m--===[&6&l  Аттрибуты &5&m]===--&3Урон: +6.0-8.0")) {
				EText.warn(
						"ItemHashConverter System: Item cannot be converted from Hash! Serialiser not works properly");
				EText.sendRaw("IN:  " + hashes.get(0));
				EText.sendRaw(
						"REQ: IRON_SWORD:1:0♥&9Iron Sword♦&4Уровень: 1◘&4Крит-Шанс: +3.0◘&5&m--===[&6&l  Аттрибуты &5&m]===--&3Урон: +6.0-8.0");
				EText.sendRaw("OUT: " + ItemStackUtil.toString(stack));
			} else
				EText.debug("ItemHashConverter System: No any problem found, remember: ♥♦♣♠ not is good game!");
		}
	}

	protected void unregisterListeners() {
		HandlerList.unregisterAll(getEventLoader().mainPlugin);
	}

	public void testSettings() {
		EText.debug("Settings System: Checking..");
		final DoubleSettings sts = new DoubleSettings();

		sts.addBase("player.equip.chestplate.health", 2);
		sts.addBase("player.equip.chestplate.damage", 2);
		sts.addBase("player.equip.helmet.health", 2);
		sts.addBase("player.equip.helmet.damage", 2);
		sts.addBase("player.equip.leggings.health", 2);
		sts.addBase("player.equip.leggings.damage", 2);
		sts.addBase("player.equip.boots.health", 2);
		sts.addBase("player.equip.boots.damage", 2);
		sts.addBase("player.equip.hand.health", 2);
		sts.addBase("player.equip.hand.damage", 2);
		sts.addBase("player.equip.offhand.health", 2);
		sts.addBase("player.equip.offhand.damage", 2);
		sts.removeKey("helmet");
		sts.removeKey("chestplate");
		sts.removeKey("leggings");
		sts.removeKey("boots");
		sts.removeKey("equip");
		sts.addValue("player.equip.chestplate.health", 2, 2.2);
		sts.addRange("player.equip.chestplate.damage", 2, 3.3);
		sts.addBase("player.equip.helmet.health", 2);
		sts.addBase("player.equip.helmet.damage", 2);
		sts.addBase("player.equip.leggings.health", 2);
		sts.addBase("player.equip.leggings.damage", 2);
		sts.addBase("player.equip.boots.health", 2);
		sts.addBase("player.equip.boots.damage", 2);
		sts.addBase("player.equip.hand.health", 2);
		sts.addBase("player.equip.hand.damage", 2);
		sts.addBase("player.equip.offhand.health", 2);
		sts.addBase("player.equip.offhand.damage", 2);

		if (sts.getSettingsSize() == 14)
			EText.debug("Settings System: Works correctly!");
		else {
			EText.warn("Settings System: Some problem found.. Please tell to author about with text above!");
			sts.dumpToConsole();
		}
	}

	public static void loadModules() {
		final File folder = new File(Core.instance().getDataFolder(), "modules");
		folder.mkdirs();

		if (folder.exists() && folder.isDirectory()) {
			final File[] addons = folder.listFiles();
			if (addons != null)
				for (final File addon : addons)
					if (!addon.isDirectory() && addon.getName().endsWith(".jar")) {
						final String name = addon.getName().replace(".jar", "").substring(0,
								addon.getName().indexOf("-"));


						if (!loadedJars.contains(addon.getName().toLowerCase())) {
							if (!getCfg().contains("modules.enable-" + name.toLowerCase()))
								EText.warn("New module &a'" + addon.getName() +
										"'&4 founded, enable it in config if you need this");
							if (getCfg().getBoolean("modules.enable-" + name.toLowerCase(), false, true))
								loadModule(addon);
						}
					}
		}
	}

	private static void loadModule(File addon) {
		try(final JarFile jAddon = new JarFile(addon);) {
			final Enumeration<JarEntry> entry = jAddon.entries();

			final URL[] urls = { new URL("jar:file:" + addon.getPath() + "!/") };
			final ClassLoader cl = URLClassLoader.newInstance(urls, Core.instance.getClassLoader());

			while (entry.hasMoreElements()) {
				final JarEntry je = entry.nextElement();

				if (!je.isDirectory() && je.getName().endsWith(".class")) {

					String className = je.getName().substring(0, je.getName().length() - 6);
					className = className.replace("/", ".");

					try {
						final Class<?> c = Class.forName(className, false, cl);
						if (EJAddon.class.isAssignableFrom(c)) {
							final Object instance = c.getConstructor().newInstance();
							final String name = (String)c.getMethod("getModuleName").invoke(instance);

							eJModules.put(name.toLowerCase(), (EJModule) instance);
							loadedJars.add(addon.getName().toLowerCase());

							c.getMethod("loadModule").invoke(instance);
						}
					} catch (final Exception e) {
						EText.warn("Unable to load module from file: " + addon.getName());
						e.printStackTrace();
					}
				}
			}
		} catch (final Exception e) {
			EText.warn("Unable to load module from file: " + addon.getName());
			e.printStackTrace();
			return;
		}
	}

	public static boolean isRegistered(String moduleName) {
		return eJModules.containsKey(moduleName.toLowerCase());
	}

	public static void reloadModules() {
		for (final EJModule module : eJModules.values())
			module.reloadModule();
	}

}
