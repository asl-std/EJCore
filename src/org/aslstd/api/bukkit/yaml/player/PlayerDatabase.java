package org.aslstd.api.bukkit.yaml.player;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.aslstd.api.bukkit.yaml.YAML;
import org.aslstd.api.ejcore.util.Obj;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;

public class PlayerDatabase {

	@Getter private static final ConcurrentMap<String, PlayerDatabase> databases = new ConcurrentHashMap<>();


	public static @NotNull PlayerDatabase createDatabase(@NotNull JavaPlugin plugin) {
		Obj.checkNull(plugin);

		if (databases.containsKey(plugin.getName()))
			return databases.get(plugin.getName());
		else {
			final PlayerDatabase db = new PlayerDatabase(plugin);
			databases.put(plugin.getName(), db);
			return db;
		}
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
		if (!folder.exists())
			folder.mkdirs();

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

	public @NotNull YAML getPlayerFile(@NotNull OfflinePlayer player) {
		Obj.checkNull(player);
		YAML pfile = database.get(player.getUniqueId());

		if (pfile == null)
			pfile = YAML.of("players/" + player.getUniqueId() + ".yml", plugin);

		return pfile;
	}

}
