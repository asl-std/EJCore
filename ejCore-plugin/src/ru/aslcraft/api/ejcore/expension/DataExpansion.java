package ru.aslcraft.api.ejcore.expension;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.PlaceholderAPI;
import ru.aslcraft.api.bukkit.yaml.YAML;
import ru.aslcraft.api.ejcore.plugin.hook.PAPI;
import ru.aslcraft.core.Core;

public class DataExpansion extends PAPI {

	public DataExpansion() {
		super(Core.instance(), "ejcdata");
	}

	@Override
	public @NotNull List<String> getPlaceholders() {
		return Arrays.asList(
				"ejcdata_player_<player-name/uid>_<key>",
				"ejcdata_custom_<file-name>_<key>"
				);
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (PlaceholderAPI.containsBracketPlaceholders(identifier))
			identifier = PlaceholderAPI.setBracketPlaceholders(p, identifier);

		final String[] params = identifier.split("_");

		if (params.length < 3) return null;

		Arrays.asList(params).forEach(Objects::requireNonNull);

		switch(params[0]) {
		case "player":
			OfflinePlayer player = Bukkit.getPlayerExact(params[1]);
			if (player == null)
				for (final OfflinePlayer ofp : Bukkit.getOfflinePlayers()) {
					final String name = ofp.getPlayerProfile().getName();
					final UUID uid = ofp.getPlayerProfile().getUniqueId();

					if (name != null && name.equalsIgnoreCase(params[1])) {
						player = ofp;
						break;
					}

					if (uid != null && uid.toString().equalsIgnoreCase(params[1])) {
						player = ofp;
						break;
					}
				}

			if (player == null) return null;

			final YAML pfile = YAML.getPlayerFile(player);

			return pfile.getString(params[2], "value not exist in this player", false);
		case "custom":
			return YAML.getCustomStorage(params[1]).getString(params[2], "value not exist in this data-file", false);
		}
		return null;
	}

}
