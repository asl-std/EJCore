package org.aslstd.api.ejcore.external;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.ejcore.worker.WorkerTask;
import org.aslstd.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import ru.zoommax.HexUtils;

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
			return file == null ? file = "ejCore-" + name().toLowerCase() + ".jar" : file;
		}

		public File file() {
			return new File(Core.instance().getDataFolder().getParent() + "/" + fileName());
		}

		public String pluginName() {
			return "ejCore-" + name().toLowerCase() + "-" + VERSION;
		}

		public String pluginNameOnly(){
			return "ejCore-" + name().toLowerCase();
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

	public enum Endpoints {
		HASH, PLUGIN;

		@Override
		public String toString(){
			return "get"+name().toLowerCase()+"?";
		}
	}

	private static volatile boolean paused = true;

	@SneakyThrows
	public static void initialize() {

		File hashDir = new File(Core.instance().getDataFolder()+"/hashdir");
		if (!hashDir.exists()){
			hashDir.mkdirs();
		}
		final String url = "https://maven.zoommax.ru/";
		ThreadLoader[] load = new ThreadLoader[Library.values().length];

		int i = 0;
		for (Library lib : Library.values()) {
			if (Core.getCfg().getBoolean("external-libs.ejcore-" + lib.toString(), false, true)) {
				load[i] = new ThreadLoader(url, lib.pluginNameOnly());
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
	@NonNull
	private String urlStr;
	@NonNull
	private String libName;

	private ProgressBarBuilder pbb = new ProgressBarBuilder()
			.setStyle(ProgressBarStyle.ASCII)
			.setUpdateIntervalMillis(1)
			.setUnit(" B", 1);

	@Override
	@SneakyThrows
	public Void get() {
		File localHashFile = new File(Core.instance().getDataFolder() + "/hashdir/" + libName + FileType.HASH);
		if (!localHashFile.exists()) {
			Files.write(localHashFile.toPath(), "0".getBytes(StandardCharsets.UTF_8));
		}
		String localHash = new String(Files.readAllBytes(localHashFile.toPath()), StandardCharsets.UTF_8);
		JSONObject hashObj = new JSONObject(GET(ExternalLoader.Endpoints.HASH));
		if (!hashObj.getBoolean("error")) {
			String externalHash = hashObj.getString("hash");
			if (externalHash.equalsIgnoreCase(localHash)) {
				EText.fine(libName + " is relevant");
			} else {
				EText.fine("Download " + libName);
				JSONObject jObj = new JSONObject(GET(ExternalLoader.Endpoints.PLUGIN));
				if (jObj.getBoolean("error")) {
					EText.warn(libName + " cannot be downloaded, file not found");
				} else {
					EText.fine("Start saving files");
					List<LoadedFiles> loadedFiles = new ArrayList<>();
					byte[] jar = Base64.getDecoder().decode(jObj.getString("jar"));
					byte[] info = Base64.getDecoder().decode(jObj.getString("info"));
					loadedFiles.add(new LoadedFiles(jar, libName, FileType.JAR.toString()));
					loadedFiles.add(new LoadedFiles(info, libName, FileType.INFO.toString()));
					saveFile(loadedFiles);
				}
			}
		} else {
			EText.warn(libName + " cannot be downloaded, file not found");
		}
		return null;
	}

	@SneakyThrows
	private String GET(ExternalLoader.Endpoints endpoint) {
		URL url = new URL(urlStr + endpoint.toString() + libName);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(ProgressBar.wrap(new InputStreamReader(con.getInputStream()), pbb.setInitialMax(con.getContentLength()).setTaskName(libName)));
		StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		String line;
		while ((line = in.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		in.close();
		return response.toString();
	}

	@SneakyThrows
	private void saveFile(List<LoadedFiles> lf){
		List<CallableFileData> lst = new ArrayList<>();
		for (LoadedFiles loadedFile : ProgressBar.wrap(lf, pbb.setTaskName("preparing files").setUnit(" files", 1))) {
			String pluginName = loadedFile.getPluginName();
			String type = loadedFile.getType();
			byte[] data = loadedFile.getData();
			File file = null;
			if (type.equalsIgnoreCase(FileType.JAR.toString())) {
				file = new File(Core.instance().getDataFolder().getParent(), pluginName + type);
			}
			if (type.equalsIgnoreCase(FileType.INFO.toString())) {
				file = new File(Core.instance().getDataFolder(), "hashdir/" + pluginName + type);
			}
			// TODO `file` FIX POTENTIAL NPE?
			try {
				Files.write(file.toPath(), data);
				lst.add(new CallableFileData(pluginName, true, type));
			} catch (IOException e) {
				lst.add(new CallableFileData(pluginName, false, type));
			}
		}

		ProgressBar pb = new ProgressBarBuilder()
				.setStyle(ProgressBarStyle.ASCII)
				.setUnit(" files", 1)
				.setInitialMax(lst.size()+1)
				.setTaskName("saved files")
				.setUpdateIntervalMillis(50)
				.build();
		for (CallableFileData fileData : lst){
			if (fileData.isSaved()){
				if (fileData.getType().equalsIgnoreCase(FileType.JAR.toString())){
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					byte[] encodedhash = digest.digest(Files.readAllBytes(new File(Core.instance().getDataFolder().getParent(), fileData.getPluginName()+fileData.getType()).toPath()));
					File hashf = new File(Core.instance().getDataFolder(), "hashdir/"+fileData.getPluginName()+FileType.HASH);
					FileWriter fileWriter = new FileWriter(hashf);
					fileWriter.write(HexUtils.toString(encodedhash));
					fileWriter.flush();
					fileWriter.close();
					pb.step();
				}
				pb.step();
			}
		}
		pb.close();
	}
}
