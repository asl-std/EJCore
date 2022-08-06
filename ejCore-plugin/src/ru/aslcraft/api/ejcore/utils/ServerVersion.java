package ru.aslcraft.api.ejcore.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import lombok.Getter;
import ru.aslcraft.api.bukkit.message.EText;

/**
 * <p>ServerVersion class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class ServerVersion {

	/** Constant <code>VER_1_5_2=1502</code> */
	/** Constant <code>VER_1_6_2=1602</code> */
	/** Constant <code>VER_1_6_4=1604</code> */
	/** Constant <code>VER_1_7_2=1702</code> */
	/** Constant <code>VER_1_7_5=1705</code> */
	/** Constant <code>VER_1_7_8=1708</code> */
	/** Constant <code>VER_1_7_9=1709</code> */
	/** Constant <code>VER_1_8_0=1800</code> */
	/** Constant <code>VER_1_8_3=1803</code> */
	/** Constant <code>VER_1_8_8=1808</code> */
	/** Constant <code>VER_1_9_0=1900</code> */
	/** Constant <code>VER_1_10_2=11002</code> */
	/** Constant <code>VER_1_11_0=11100</code> */
	/** Constant <code>VER_1_11_1=11101</code> */
	/** Constant <code>VER_1_11_2=11102</code> */
	/** Constant <code>VER_1_12_2=11202</code> */
	/** Constant <code>VER_1_13=11300</code> */
	/** Constant <code>VER_1_14=11400</code> */
	/** Constant <code>VER_1_14_4=11404</code> */
	/** Constant <code>VER_1_15=11500</code> */
	/** Constant <code>VER_1_15_2=11502</code> */
	/** Constant <code>VER_1_16=11600</code> */
	/** Constant <code>VER_1_16_4=11604</code> */
	/** Constant <code>VER_1_17=11700</code> */
	/** Constant <code>VER_1_18=11800</code> */
	public static final int
	/////////////////////// VERSION CONSTANS
	VER_1_5_2	 = 1502 ,//
	VER_1_6_2	 = 1602 ,//
	VER_1_6_4	 = 1604 ,//
	VER_1_7_2	 = 1702 ,//
	VER_1_7_5	 = 1705 ,//
	VER_1_7_8	 = 1708 ,//
	VER_1_7_9	 = 1709 ,//
	VER_1_8_0	 = 1800 ,//
	VER_1_8_3	 = 1803 ,//
	VER_1_8_8	 = 1808 ,//
	VER_1_9_0	 = 1900 ,//
	VER_1_10_2	 = 11002,//
	VER_1_11_0	 = 11100,//
	VER_1_11_1	 = 11101,//
	VER_1_11_2	 = 11102,//
	VER_1_12_2	 = 11202,//
	VER_1_13	 = 11300,//
	VER_1_14 	 = 11400,//
	VER_1_14_4	 = 11404,//
	VER_1_15	 = 11500,//
	VER_1_15_2	 = 11502,//
	VER_1_16	 = 11600,//
	VER_1_16_4	 = 11604,//
	VER_1_17	 = 11700,//
	VER_1_18	 = 11800;//
	///////////////////////


	@Getter private static int		VERSION		= -1;
	private static String	TYPE		= "UNKNOWN";

	/**
	 * <p>getOnlinePlayers.</p>
	 *
	 * @return an array of {@link org.bukkit.entity.Player} objects
	 */
	@SuppressWarnings("unchecked")
	public static Player[] getOnlinePlayers() {
		if (ServerVersion.isVersionAtLeast(ServerVersion.VER_1_8_8)) {
			final ArrayList<Player> list = new ArrayList<>();
			final Collection<? extends Player> online = Bukkit.getOnlinePlayers();
			for (final Object player : online)
				if ((player instanceof Player)) list.add((Player) player);
			return list.toArray(new Player[list.size()]);
		}
		try {
			final Object players = Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
			if ((players instanceof Player[])) return (Player[]) players;
			if ((players instanceof List)) {
				final List<Player> list = (List<Player>) players;
				return list.toArray(new Player[list.size()]);
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return new Player[0];
	}

	/**
	 * <p>init.</p>
	 *
	 * @param version a {@link java.lang.String} object
	 * @param serverType a {@link java.lang.String} object
	 */
	public static void init(String version, String serverType) {
		if (serverType != null) ServerVersion.TYPE = serverType;

		try {
			final int i = version.indexOf("-");
			if (i <= 0) return;
			final String pre = version.substring(0, i);
			final String[] pieces = pre.split("\\.");
			ServerVersion.VERSION = Integer.parseInt(pieces[0]) * 10000 + Integer.parseInt(pieces[1]) * 100;
			if (pieces.length > 2) ServerVersion.VERSION += Integer.parseInt(pieces[2]);
		} catch (final Exception e) {
			if (ServerVersion.VERSION == -1) try {
				OfflinePlayer.class.getDeclaredMethod("getUniqueId");
				ServerVersion.VERSION = 99999;
			} catch (final Exception ex) { ServerVersion.VERSION = ServerVersion.VER_1_7_2; }
		}

		EText.fine("&aServer version: &5'" + ServerVersion.TYPE + "-" + ServerVersion.VERSION + "'");
	}

	/**
	 * <p>isUUID.</p>
	 *
	 * @return a boolean
	 */
	public static boolean isUUID() { return ServerVersion.isVersionAtLeast(ServerVersion.VER_1_7_5); }
	/**
	 * <p>isVersionAtLeast.</p>
	 *
	 * @param version a int
	 * @return a boolean
	 */
	public static boolean isVersionAtLeast(int version) { return ServerVersion.VERSION < version; }
	/**
	 * <p>isVersionAtMost.</p>
	 *
	 * @param version a int
	 * @return a boolean
	 */
	public static boolean isVersionAtMost(int version) { return ServerVersion.VERSION >= version; }

}
