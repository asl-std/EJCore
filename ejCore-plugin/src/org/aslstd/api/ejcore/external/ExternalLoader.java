package org.aslstd.api.ejcore.external;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.core.Core;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

	private static final String VERSION = "1.2.18";

	public enum Library {
		BOTS, DATABASE, WEBSERVER;

		@Setter @Getter boolean downloaded;

		public static void loadLibraries() {
			Stream.of(values()).filter(l -> l.downloaded).forEach(l -> {
				ExternalLoader.loadPlugin(l.file());

			});
		}

		private String file;

		public String fileName() {
			return file == null ? file = "ejCore-" + name().toLowerCase() + "-" + VERSION + ".jar" : file;
		}

		public File file() {
			return new File(Core.instance().getDataFolder().getParent() + "/" + fileName());
		}

		public String pluginName() {
			return "ejCore-" + name().toLowerCase() + "-" + VERSION;
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
		ThreadLoader[] load = new ThreadLoader[3];

		int i = 0;
		for (Library lib : Library.values()) {
			if (Core.getCfg().getBoolean("external-libs.ejcore-" + lib.toString(), false, true)) {
				load[i] = new ThreadLoader(url+lib.fileName(), lib);
				EText.fine("Downloading library: " + lib.toString());
				i++;
			}
		}

		Stream.of(load).filter(Objects::nonNull).forEach(r -> service.submit(r));

		//Thread.currentThread().join();
	}
}

@RequiredArgsConstructor
class ThreadLoader implements Runnable {
	@NonNull private String urlStr;
	@NonNull private ExternalLoader.Library lib;

	@Getter boolean completed;

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
		} finally {
			completed = true;
		}
	}
}
