package ru.asl.api.ejcore.resolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.message.EText;

public class ServerVersion {

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
			VER_1_14_4	 = 11404;//
			///////////////////////


	private static int		VERSION		= -1;
	private static String	TYPE		= "UNKNOWN";

	@SuppressWarnings("unchecked")
	public static Player[] getOnlinePlayers() {
		if (ServerVersion.isVersionAtLeast(ServerVersion.VER_1_8_8)) {
			ArrayList<Player> list = new ArrayList<>();
			Collection<? extends Player> online = Bukkit.getOnlinePlayers();
			for (Object player : online)
				if ((player instanceof Player)) list.add((Player) player);
			return list.toArray(new Player[list.size()]);
		}
		try {
			Object players = Bukkit.class.getMethod("getOnlinePlayers", new Class[0]).invoke(null, new Object[0]);
			if ((players instanceof Player[])) return (Player[]) players;
			if ((players instanceof List)) {
				List<Player> list = (List<Player>) players;
				return list.toArray(new Player[list.size()]);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new Player[0];
	}

	public static void init(String version, String serverType) {
		if (serverType != null) ServerVersion.TYPE = serverType;

		try {
			int i = version.indexOf("-");
			if (i <= 0) return;
			String pre = version.substring(0, i);
			String[] pieces = pre.split("\\.");
			ServerVersion.VERSION = Integer.parseInt(pieces[0]) * 10000 + Integer.parseInt(pieces[1]) * 100;
			if (pieces.length > 2) ServerVersion.VERSION += Integer.parseInt(pieces[2]);
		} catch (Exception e) {
			if (ServerVersion.VERSION == -1) try {
				OfflinePlayer.class.getDeclaredMethod("getUniqueId", new Class[0]);
				ServerVersion.VERSION = 99999;
			} catch (Exception ex) { ServerVersion.VERSION = ServerVersion.VER_1_7_2; }
		}

		EText.fine("&aServer version: &5'" + ServerVersion.TYPE + "-" + ServerVersion.VERSION + "'");
	}

	public static boolean isUUID() { return ServerVersion.isVersionAtLeast(ServerVersion.VER_1_7_5); }
	public static boolean isVersionAtLeast(int version) { return ServerVersion.VERSION <= version; }
	public static boolean isVersionAtMost(int version) { return ServerVersion.VERSION > version; }

}
