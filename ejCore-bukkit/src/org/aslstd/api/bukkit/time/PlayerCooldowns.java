package org.aslstd.api.bukkit.time;

import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.value.util.ValueUtil;
import org.bukkit.entity.Player;

/**
 * <p>Cooldown class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class PlayerCooldowns {

	/**
	 * <p>removeCooldown.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param key a {@link java.lang.String} object
	 */
	public static void removeCooldown(Player p, String key) {
		final EPlayer ejp = EPlayer.getEPlayer(p);

		if (!ejp.getSettings().hasKey(key)) return;

		ejp.getSettings().remove(key);
	}

	/**
	 * <p>setCooldown.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param key a {@link java.lang.String} object
	 * @param millis a long
	 */
	public static void setCooldown(Player p, String key, long millis) {
		final EPlayer ejp = EPlayer.getEPlayer(p);

		ejp.getSettings().setValue(key, System.currentTimeMillis() + millis + "");
	}

	/**
	 * <p>getCooldown.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param key a {@link java.lang.String} object
	 * @return a long
	 */
	public static long getCooldown(Player p, String key) {
		final EPlayer ejp = EPlayer.getEPlayer(p);

		if (ejp.getSettings().hasKey(key)) {
			final String val = ejp.getSettings().getValue(key);

			if (!ValueUtil.isNumber(val)) return 0L;

			final long cd = ValueUtil.parseLong(val);

			return cd - System.currentTimeMillis();
		}

		return 0L;
	}

	/**
	 * <p>isCooldownEnded.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @param key a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean isCooldownEnded(Player p, String key) {
		final EPlayer ejp = EPlayer.getEPlayer(p);

		if (!ejp.getSettings().hasKey(key)) return true;
		if (getCooldown(p, key) <= 0) {
			removeCooldown(p, key);
			return true;
		}
		return false;
	}

}
