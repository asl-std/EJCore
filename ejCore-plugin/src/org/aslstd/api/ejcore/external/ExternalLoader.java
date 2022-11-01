package org.aslstd.api.ejcore.external;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.core.Core;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

public class ExternalLoader {

	static void loadPlugin(File jarFile) {
		try {
			final Plugin plugin = Core.instance().getPluginLoader().loadPlugin(jarFile);
			if (plugin == null) throw new InvalidPluginException("Something went wrong while loading plugin");
		} catch (UnknownDependencyException | InvalidPluginException e) {
			EText.warn("Plugin " + jarFile.getName() + " could't be loaded");
		}
	}

	private static final List<File> libs = new ArrayList<>(3);
	private static final String VERSION = "1.2.18";

	public enum Library {
		BOTS, DATABASE, WEBSERVER;

		@Setter boolean downloaded;

		public static void loadLibraries() {
			Stream.of(values()).filter(l -> l.downloaded).forEach(l -> {
				ExternalLoader.loadPlugin(l.file());
				EText.debug("Loading library " + l.name() );
			});
		}

		private String file;

		public String fileName() {
			return file == null ? file = "ejCore-" + name().toLowerCase() + "-" + VERSION + ".jar" : file;
		}

		public File file() {
			return new File(Core.instance().getDataFolder().getParent() + "/" + fileName());
		}

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	@SneakyThrows
	public static void initialize() {

		final String url = "https://maven.zoommax.ru/maven/";
		final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		Runnable[] load = new Runnable[3];

		int i = 0;
		for (Library lib : Library.values()) {
			if (Core.getCfg().getBoolean("external-libs.ejcore-" + lib.toString(), true, true)) {
				load[i] = new ThreadLoader(url+lib.fileName(), lib);
				EText.fine("Downloading library: " + lib.toString());
				i++;
			}
		}

		Stream.of(load).filter(Objects::nonNull).forEach(r -> service.submit(r));
	}
}

@AllArgsConstructor
class ThreadLoader implements Runnable {
	private String urlStr;
	private ExternalLoader.Library lib;

	@Override
	@SneakyThrows
	public void run() {
		try (ReadableByteChannel rbc = Channels.newChannel(new URL(urlStr).openStream());
				FileOutputStream fos = new FileOutputStream(lib.file())) {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			lib.downloaded = true;
		} catch (Exception e) {
			EText.warn("File cannot be downloaded: " + lib.fileName());
			e.printStackTrace();
		}
	}
}
