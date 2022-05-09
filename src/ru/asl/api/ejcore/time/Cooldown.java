package ru.asl.api.ejcore.time;

import org.bukkit.entity.Player;

import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.entity.interfaze.EJPlayer;
import ru.asl.api.ejcore.value.util.ValueUtil;

public final class Cooldown {

	public static void removeCooldown(Player p, String key) {
		final EJPlayer ejp = EPlayer.getEPlayer(p);

		if (!ejp.getSettings().hasKey(key)) return;

		ejp.getSettings().remove(key);
	}

	public static void setCooldown(Player p, String key, long millis) {
		final EJPlayer ejp = EPlayer.getEPlayer(p);

		ejp.getSettings().setValue(key, System.currentTimeMillis() + millis + "");
	}

	public static long getCooldown(Player p, String key) {
		final EJPlayer ejp = EPlayer.getEPlayer(p);

		if (ejp.getSettings().hasKey(key)) {
			final String val = ejp.getSettings().getValue(key);

			if (!ValueUtil.isNumber(val)) return 0L;

			final long cd = ValueUtil.parseLong(val);

			return cd - System.currentTimeMillis();
		}

		return 0L;
	}

	public static boolean isCooldownEnded(Player p, String key) {
		final EJPlayer ejp = EPlayer.getEPlayer(p);

		if (!ejp.getSettings().hasKey(key)) return true;
		if (getCooldown(p, key) <= 0) {
			removeCooldown(p, key);
			return true;
		}
		return false;
	}

}
