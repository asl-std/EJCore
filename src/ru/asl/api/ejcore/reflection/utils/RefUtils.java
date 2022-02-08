package ru.asl.api.ejcore.reflection.utils;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import ru.asl.api.bukkit.message.EText;

public class RefUtils {

	public static String cbPackage  = "org.bukkit.craftbukkit.UNSPECIFIC.";
	public static String nmsPackage = "net.minecraft.server.UNSPECIFIC.";

	public RefUtils() { init(); }

	public static Class<?> getClass(String name) { try { return Class.forName(name); } catch(final Exception e) { return null; } }
	public static Class<?> getNMS(String name) { return getClass(nmsPackage + name); }
	public static Class<?> getCraft(String name) { return getClass(cbPackage + name); }

	private void init() {
		if (Bukkit.getServer() == null)
		{ EText.warn("Unknown error has occurred, Reflection's not works, some features dropped out"); return; }

		final Server srv = Bukkit.getServer();
		final Class<?> bktServer = srv.getClass();
		String[] pckg = bktServer.getName().split("\\.");
		String debug = "Reflections { CraftBukkit:";

		if (pckg.length == 5) {
			cbPackage.replace("UNSPECIFIC", pckg[3]);
			debug += pckg[3] + " | NMS:";
		} else
			debug += "Unknown | NMS:";

		try {
			final Method getHandle = bktServer.getDeclaredMethod("getHandle");
			final Object handle = getHandle.invoke(srv);
			final Class<?> hndlClass = handle.getClass();

			pckg = hndlClass.getName().split("\\.");
			if (pckg.length == 5) {
				nmsPackage.replace("UNSPECIFIC", pckg[3]);
				debug += pckg[3] + " }";
			} else
				debug += "Unknown }";

			EText.fine(debug);
		} catch(final Exception ex) {
			EText.warn("Some error has occurred, NMS not specified, some features dropped out"); ex.printStackTrace();
		}
	}
}
