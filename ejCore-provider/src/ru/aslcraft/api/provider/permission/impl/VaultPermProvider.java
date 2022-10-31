package ru.aslcraft.api.provider.permission.impl;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;
import ru.aslcraft.api.provider.permission.PermProvider;

public class VaultPermProvider extends PermProvider {

	private static Permission perm;

	public VaultPermProvider(JavaPlugin plugin, Permission provider) {
		super(plugin);
		perm = provider;
	}

	@Override
	public void add(Player player, String permission) {
		perm.playerAdd(player, permission);
	}

	@Override
	public void remove(Player player, String permission) {
		perm.playerRemove(player, permission);
	}

	@Override
	public boolean has(Player player, String permission) {
		return perm.playerHas(player, permission);
	}

}