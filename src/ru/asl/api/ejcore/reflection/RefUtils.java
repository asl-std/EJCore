package ru.asl.api.ejcore.reflection;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import ru.asl.api.bukkit.message.EText;

public class RefUtils {

	public static String 	cbPackage  = "org.bukkit.craftbukkit.UNSPECIFIC",
							nmsPackage = "net.minecraft.server.UNSPECIFIC";

	public RefUtils() { init(); }

	private void init() {
		if (Bukkit.getServer() == null)
			{ EText.warn("Unknown error has occured, Reflection's not works, some features dropped out"); return; }

		Server srv = Bukkit.getServer();
		Class<?> bktServer = srv.getClass();
		String[] pckg = bktServer.getName().split("\\.");
		String debug = "Reflections { CraftBukkit:";

		if (pckg.length == 5) {
			cbPackage.replace("UNSPECIFIC", pckg[3]);
			debug += pckg[3] + " | NMS:";
		} else
			debug += "Unknown | NMS:";

		try {
			Method getHandle = bktServer.getDeclaredMethod("getHandle");
			Object handle = getHandle.invoke(srv);
			Class<?> hndlClass = handle.getClass();

			pckg = hndlClass.getName().split("\\.");
			if (pckg.length == 5) {
				nmsPackage.replace("UNSPECIFIC", pckg[3]);
				debug += pckg[3] + " }";
			} else
				debug += "Unknown }";

			EText.fine(debug);
		} catch(Exception ex) {
			EText.warn("Some error has occured, NMS not specified, some features dropped out"); ex.printStackTrace();
		}
	}
}
