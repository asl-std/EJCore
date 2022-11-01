package org.aslstd.api.ejcore.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import org.aslstd.api.bukkit.message.EText;
import org.aslstd.core.Core;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

public class InternalLoader {

	public static void loadPlugin(File jarFile) {
		try {
			final Plugin plugin = Core.instance().getPluginLoader().loadPlugin(jarFile);
			if (plugin == null) throw new InvalidPluginException("Something went wrong while loading plugin");
		} catch (UnknownDependencyException | InvalidPluginException e) {
			EText.warn("Plugin " + jarFile.getName() + " could't be loaded");
		}
	}


	@SneakyThrows
	public void initialize() {
		// PLUGIN DOWNLOADING LOGIC


		// File downloaded = DOWNLOADED METHOD
		// loadPlugin(downloaded)

		boolean db = true;
		boolean bot = true;
		boolean webs = true;
		String url = "https://maven.zoommax.ru/maven/";
		String furl = Core.instance().getDataFolder().getParent();
		ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		Runnable[] load = new Runnable[3];
		if (db){
			Runnable run = new ThreadLoader(url+"ejCore-database-1.2.18.jar", furl+"/ejCore-database-1.2.18.jar");
			load[0] = run;
			//loadPlugin(new File(furl+"/ejCore-database-1.2.18.jar"));
		}
		if (bot){
			Runnable run = new ThreadLoader(url+"ejCore-bots-1.2.18.jar", furl+"/ejCore-bots-1.2.18.jar");
			load[1] = run;
			//loadPlugin(new File(furl+"/ejCore-bots-1.2.18.jar"));
		}
		if (webs){
			Runnable run = new ThreadLoader(url+"ejCore-webserver-1.2.18.jar", furl+"/ejCore-webserver-1.2.18.jar");
			load[2] = run;
			//loadPlugin(new File(furl+"/ejCore-webserver-1.2.18.jar"));
		}
		for (Runnable run : load){
			service.submit(run);
			EText.warn("loaded");
		}
		EText.warn("LOADED");
	}
}

class ThreadLoader implements Runnable{
	String urlStr, file;
	public ThreadLoader(String urlStr, String jarFilePath){
		this.urlStr = urlStr;
		this.file = jarFilePath;
	}

	@Override
	@SneakyThrows
	public void run() {
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
		//InternalLoader.loadPlugin(new File(file));
	}
}
