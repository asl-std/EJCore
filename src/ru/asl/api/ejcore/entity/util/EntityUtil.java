package ru.asl.api.ejcore.entity.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.location.Area3D;
import ru.asl.api.bukkit.location.Vector3D;

/**
 * <p>EntityUtil class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class EntityUtil {

	/**
	 * <p>getNearbyEntities.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param x a double
	 * @param y a double
	 * @param z a double
	 * @return a {@link java.util.List} object
	 */
	public static List<Entity> getNearbyEntities(Player p, double x, double y, double z) {
		final List<Entity> entities = new ArrayList<>();

		final Area3D area = getAreaAroundPlayer(p, x, y, z);

		for (final Entity entity : p.getWorld().getLivingEntities())
			if (area.isInArea2D(entity.getLocation()))
				entities.add(entity);

		return entities;
	}

	/**
	 * <p>getNearbyPlayers.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param x a double
	 * @param y a double
	 * @param z a double
	 * @return a {@link java.util.List} object
	 */
	public static List<Player> getNearbyPlayers(Player p, double x, double y, double z) {
		final List<Player> players = new ArrayList<>();

		final Area3D area = getAreaAroundPlayer(p, x, y, z);

		for (final Player player : p.getWorld().getPlayers())
			if (area.isInArea2D(player.getLocation()))
				players.add(player);

		return players;
	}

	/**
	 * <p>getAreaAroundPlayer.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param x a double
	 * @param y a double
	 * @param z a double
	 * @return a {@link ru.asl.api.bukkit.location.Area3D} object
	 */
	public static Area3D getAreaAroundPlayer(Player p, double x, double y, double z) {
		final Vector3D origin = new Vector3D(x/2,y/2,z/2);

		return new Area3D(Vector3D.fromLocation(p.getLocation()).substract(origin), Vector3D.fromLocation(p.getLocation()).add(origin));
	}

}
