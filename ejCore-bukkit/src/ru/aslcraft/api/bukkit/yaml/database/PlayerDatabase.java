package ru.aslcraft.api.bukkit.yaml.database;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import ru.aslcraft.api.bukkit.yaml.YAML;

public class PlayerDatabase {

	@Getter private static final ConcurrentMap<String, PlayerDatabase> databases = new ConcurrentHashMap<>();

	private static boolean lock;

	public static PlayerDatabase createDatabase(JavaPlugin plugin) {
		if (lock) return null;

		if (databases.containsKey(plugin.getName()))
			return databases.get(plugin.getName());
		else
			return new PlayerDatabase(plugin);
	}

	public static void lock() {
		lock = true;
	}

	private JavaPlugin plugin;
	private ConcurrentMap<UUID, YAML> database;

	private PlayerDatabase(JavaPlugin plugin) {
		this.plugin = plugin;
		database = new ConcurrentHashMap<>();
		initDatabase();
	}

	private void initDatabase() {
		final File folder = new File(plugin.getDataFolder() + "/players");

		for (final File file : folder.listFiles()) {
			if (file.isFile() && YAML.getFileExtension(file).equalsIgnoreCase("yml")) {
				final YAML pfile = new YAML(file, plugin, null);
				final String name = file.getName().substring(0, file.getName().lastIndexOf("."));
				UUID uid;
				try {
					uid = UUID.fromString(name);
				} catch(final Exception e) { continue; }
				if (uid == null) continue;

				database.put(uid, pfile);
			}
		}
	}

	public YAML getPlayerFile(OfflinePlayer player) {
		YAML pfile = database.get(player.getUniqueId());

		if (pfile == null)
			pfile = YAML.of("players/" + player.getUniqueId() + ".yml", plugin);

		return pfile;
	}

}
