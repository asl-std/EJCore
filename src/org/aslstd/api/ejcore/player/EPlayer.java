package org.aslstd.api.ejcore.player;

import java.util.UUID;

import org.aslstd.api.bukkit.entity.pick.UPlayer;
import org.aslstd.api.bukkit.equip.EquipInventory;
import org.aslstd.api.bukkit.storage.PlayerFileStorage;
import org.aslstd.api.bukkit.yaml.Yaml;
import org.aslstd.api.ejcore.collection.Stash;
import org.aslstd.core.Core;
import org.bukkit.Bukkit;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public final class EPlayer extends UPlayer {

	@Getter private static Stash<UUID, EPlayer> stash = Stash.of(EPlayer::new, e -> e.options().save());

	@Getter private Options options;

	@Getter private EquipInventory equip;
	@Getter private Yaml dataFile;

	public EPlayer(UUID uid) {
		super(Bukkit.getPlayer(uid));
		dataFile = PlayerFileStorage.getDatabases().get(Core.instance().getName()).getPlayerFile(player);
		options = new Options(this);
		equip = new EquipInventory();
	}

}

