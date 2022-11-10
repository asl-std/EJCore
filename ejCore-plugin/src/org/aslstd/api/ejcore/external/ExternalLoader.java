package org.aslstd.api.ejcore.external;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarConsumer;
import me.tongfei.progressbar.ProgressBarStyle;
import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.ejcore.worker.WorkerTask;
import org.aslstd.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

public class ExternalLoader {

	static Plugin loadPlugin(File jarFile) {
		try {
			final Plugin plugin = Bukkit.getServer().getPluginManager().loadPlugin(jarFile);
			if (plugin == null) throw new InvalidPluginException("Something went wrong while loading plugin");
			return plugin;
		} catch (UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e) {
			EText.warn("Plugin " + jarFile.getName() + " could't be loaded");
		}
		return null;
	}

	static void enablePlugin(Plugin plugin) {
		if (!plugin.isEnabled())
			Bukkit.getServer().getPluginManager().enablePlugin(plugin);
	}

	private static final String VERSION = "1.2.18";

	public enum Library {
		BOTS, DATABASE, WEBSERVER;

		private String file;
		@Getter private Plugin plugin;

		public String fileName() {
			return file == null ? file = "ejCore-" + name().toLowerCase() + "-" + VERSION + ".jar" : file;
		}

		public File file() {
			return new File(Core.instance().getDataFolder().getParent() + "/" + fileName());
		}

		public String pluginName() {
			return "ejCore-" + name().toLowerCase() + "-" + VERSION;
		}

		public void loadPlugin() {
			final File file = file();
			if (file.exists())
				plugin = ExternalLoader.loadPlugin(file);
		}

		public void enablePlugin() {
			if (plugin != null)
				ExternalLoader.enablePlugin(plugin);
		}

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	private static volatile boolean paused = true;

	@SneakyThrows
	public static void initialize() {

		final String url = "https://maven.zoommax.ru/maven/";
		ThreadLoader[] load = new ThreadLoader[3];

		int i = 0;
		for (Library lib : Library.values()) {
			if (Core.getCfg().getBoolean("external-libs.ejcore-" + lib.toString(), false, true)) {
				load[i] = new ThreadLoader(url+lib.fileName(), lib);
				EText.fine("Downloading library: " + lib.toString());
				i++;
			}
		}

		Core.getWorkers().getWorker().thenApplyAsync(w -> {
			Stream.of(load).filter(Objects::nonNull).forEachOrdered(r -> {
				w.queueTask(new WorkerTask<>(r));
			});

			return w.invokeAll();
		}).whenComplete((s,t) -> {
			if (s == null) return;
			while(!s.allWorksCompleted()) { /* waiting */ }

			ExternalLoader.unpause();
		});

		while(paused) { /* waiting */ }

	}

	private static void unpause() {
		paused = false;
	}
}

@RequiredArgsConstructor
class ThreadLoader implements Supplier<Void> {
	@NonNull private String urlStr;
	@NonNull private ExternalLoader.Library lib;

	@Override
	@SneakyThrows
	public Void get() {
		long size;
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		size = conn.getContentLength();
		ProgressBarBuilder pbb = new ProgressBarBuilder()
				.setTaskName(lib.toString())
				.setStyle(ProgressBarStyle.ASCII)
				.setUpdateIntervalMillis(10)
				.setInitialMax(size);
		try (ReadableByteChannel rbc = Channels.newChannel(ProgressBar.wrap(new URL(urlStr).openStream(), pbb));
				FileOutputStream fos = new FileOutputStream(lib.file())) {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

		} catch (Exception e) {
			EText.warn("File cannot be downloaded: " + lib.fileName());
			e.printStackTrace();
		}
		return null;
	}
}
