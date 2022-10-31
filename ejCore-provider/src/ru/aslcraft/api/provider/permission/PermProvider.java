package ru.aslcraft.api.provider.permission;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableList;

import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.permission.Permission;
import ru.aslcraft.api.provider.permission.impl.BukkitPermProvider;
import ru.aslcraft.api.provider.permission.impl.LuckPermProvider;
import ru.aslcraft.api.provider.permission.impl.VaultPermProvider;

public abstract class PermProvider {

	private static PermProvider instance;

	public static void initialize(JavaPlugin initializer) {
		if (instance != null) return;

		if (checkExists("net.luckperms.api.LuckPerms")) {
			final RegisteredServiceProvider<LuckPerms> provider = checkService(LuckPerms.class);
			if (provider != null) {
				instance = new LuckPermProvider(initializer, provider.getProvider());
				return;
			}
		}

		if (checkExists("net.milkbowl.vault.permission.Permission")) {
			final RegisteredServiceProvider<Permission> provider = checkService(Permission.class);
			if (provider != null) {
				instance = new VaultPermProvider(initializer, provider.getProvider());
			}
		}
	}

	public static PermProvider get(JavaPlugin plugin) {
		if (instance == null)
			return new BukkitPermProvider(plugin);
		return instance;
	}

	private static <P> RegisteredServiceProvider<P> checkService(Class<P> clazz) {
		return Bukkit.getServicesManager().getRegistration(clazz);
	}

	private static boolean checkExists(String clazz) {
		try {
			Class.forName(clazz); return true;
		} catch (final ClassNotFoundException e) {
			return false;
		}
	}

	protected JavaPlugin plugin;

	public PermProvider(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public abstract void add(Player player, String permission);

	public void add(Player player, String... permissions) {
		for (final String permission : permissions)
			add(player, permission);
	}

	public void add(@Nonnull Player player, List<String> permissions) {
		ImmutableList.of(permissions).stream().filter(Objects::nonNull).forEach(permission -> add(player, permission) );
	}

	public abstract void remove(Player player, String permission);

	public void remove(Player player, String... permissions) {
		for (final String permission : permissions)
			remove(player, permission);
	}

	public void remove(Player player, List<String> permissions) {
		ImmutableList.of(permissions).forEach(permission -> remove(player, permission) );
	}

	public abstract boolean has(Player player, String permission);

	public boolean has(Player player, String... permissions) {
		for (final String permission : permissions)
			if (!has(player, permission))
				return false;
		return true;
	}

	public boolean has(Player player, List<String> permissions) {
		for (final String permission : ImmutableList.copyOf(permissions))
			if (!has(player, permission))
				return false;
		return true;
	}

}
