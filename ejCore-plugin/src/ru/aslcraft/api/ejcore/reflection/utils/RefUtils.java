package ru.aslcraft.api.ejcore.reflection.utils;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.api.ejcore.utils.ServerVersion;

/**
 * <p>RefUtils class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class RefUtils {

	/** Constant <code>CRAFTBUKKIT="org.bukkit.craftbukkit.UNSPECIFIC."</code> */
	public static final String CRAFTBUKKIT  = "org.bukkit.craftbukkit.UNSPECIFIC.";
	/** Constant <code>NMS="net.minecraft.server.UNSPECIFIC."</code> */
	public static final String NMS = "net.minecraft.server.UNSPECIFIC.";

	/**
	 * <p>Constructor for RefUtils.</p>
	 */
	public RefUtils() { init(); }

	/**
	 * <p>getClass.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link java.lang.Class} object
	 */
	public static Class<?> getClass(String name) { try { return Class.forName(name); } catch(final Exception e) { return null; } }
	/**
	 * <p>getNMS.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link java.lang.Class} object
	 */
	public static Class<?> getNMS(String name) { return getClass(NMS + name); }
	/**
	 * <p>getCraft.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @return a {@link java.lang.Class} object
	 */
	public static Class<?> getCraft(String name) { return getClass(CRAFTBUKKIT + name); }

	private void init() {
		if (Bukkit.getServer() == null)
		{ EText.warn("Unknown error has occurred, Reflection's not works, some features dropped out"); return; }

		final Server srv = Bukkit.getServer();
		final Class<?> bktServer = srv.getClass();
		String[] pckg = bktServer.getName().split("\\.");
		String debug = "Reflections { CraftBukkit:";

		if (pckg.length == 5) {
			CRAFTBUKKIT.replace("UNSPECIFIC", pckg[3]);
			debug += pckg[3] + " | NMS:";
		} else
			debug += "Unknown | NMS:";

		try {
			if (ServerVersion.isVersionAtLeast(ServerVersion.VER_1_18)) {
				final Method getHandle = bktServer.getDeclaredMethod("getHandle");
				final Object handle = getHandle.invoke(srv);
				final Class<?> hndlClass = handle.getClass();

				pckg = hndlClass.getName().split("\\.");
			}

			if (pckg.length == 5) {
				NMS.replace("UNSPECIFIC", pckg[3]);
				debug += pckg[3] + " }";
			} else
				debug += "Unknown }";

			EText.fine(debug);
		} catch(final Exception ex) {
			EText.warn("Some error has occurred, NMS not specified, some features dropped out"); ex.printStackTrace();
		}
	}
}
