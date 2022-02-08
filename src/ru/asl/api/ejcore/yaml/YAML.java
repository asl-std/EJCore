package ru.asl.api.ejcore.yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.message.EText;
import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.core.Core;

/**
 * Basic wrapper for default .yml files
 */
public class YAML {

	protected YamlConfiguration	yaml	= new YamlConfiguration();
	protected File				file;

	public YAML(File file, EJPlugin plugin, String extendedPath) {
		this.file = file;
		try {
			if (fileExists())
				load();
			else {

				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();

				if (plugin != null && plugin.getResource(file.getName()) != null) {
					final String ex = extendedPath;
					if (ex == null)
						exportFile(file.getName(), plugin, plugin.getDataFolder());
					else
						exportFile(file.getName(), plugin, plugin.getDataFolder() + "/" + ex);
					load();
				}
				else {
					file.createNewFile();
					load();
				}
			}
		} catch (IOException | InvalidConfigurationException e) {
			file = null;
			yaml = null;
			e.printStackTrace();
		}
	}

	public YAML(String path, EJPlugin plugin) { this(new File(path), plugin, null); }

	public YAML(String path) { this(new File(path), null, null); }

	public YAML(File file) { this(file, null, null); }

	public boolean contains(String path) {
		return yaml.contains(path);
	}

	protected boolean fileExists() { return file.exists(); }
	public boolean getBoolean(String path) {
		return yaml.getBoolean(path);
	}

	public boolean getBoolean(String path, boolean def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return yaml.getBoolean(path);
	}

	public double getDouble(String path) {
		return yaml.getDouble(path);
	}

	public double getDouble(String path, double def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getDouble(path);
	}

	public List<Double>  getDoubleList(String path) { return yaml.getDoubleList(path); }

	public File getFile() { return file; }

	public float getFloat(String path) {
		final double request = this.getDouble(path);
		return request <= Float.MIN_VALUE ? Float.MIN_VALUE : request >= Float.MAX_VALUE ? Float.MAX_VALUE : Double.valueOf(request).floatValue();
	}

	public float getFloat(String path, float def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getFloat(path);
	}

	public List<Float>   getFloatList(String path) { return yaml.getFloatList(path); }

	public int getInt(String path) {
		final long request = this.getLong(path);
		return request <= Integer.MIN_VALUE ? Integer.MIN_VALUE : request >= Integer.MAX_VALUE ? Integer.MAX_VALUE : Long.valueOf(request).shortValue();
	}

	public int getInt(String path, int def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getInt(path);
	}

	public List<Integer> getIntList(String path) { return yaml.getIntegerList(path); }

	public Set<String> getKeys(boolean deep) {
		return yaml.getKeys(deep);
	}

	public long getLong(String path) {
		return yaml.getLong(path);
	}

	public long getLong(String path, long def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getLong(path);
	}

	public List<Long>    getLongList(String path) { return yaml.getLongList(path); }

	public ConfigurationSection getSection(String section) {
		return yaml.getConfigurationSection(section);
	}

	public short getShort(String path) {
		final long request = this.getLong(path);
		return request <= Short.MIN_VALUE ? Short.MIN_VALUE : request >= Short.MAX_VALUE ? Short.MAX_VALUE : Long.valueOf(request).shortValue();
	}

	public short getShort(String path, short def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getShort(path);
	}

	public List<Short> getShortList(String path) { return yaml.getShortList(path); }

	public String getString(String path) {
		return yaml.getString(path);
	}

	public String getString(String path, String def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getString(path);
	}
	public List<String>  getStringList(String path) {
		return yaml.getStringList(path);
	}
	public void load() throws FileNotFoundException, IOException, InvalidConfigurationException { yaml.load(file); }
	public void reload() {
		try {
			load();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	public void save() { try { yaml.save(file); } catch (final IOException e) { e.printStackTrace(); } }
	public void set(String path, Object value) {
		yaml.set(path, value);
		save();
	}

	public static String getFileExtension(File file) {
		final String fileName = file.getName();

		if (fileName.lastIndexOf(".") > 0) return fileName.substring(fileName.lastIndexOf(".") + 1);
		else return "";
	}

	public static YAML getPlayerFile(Player p) {
		final YAML yaml = new YAML(new File(Core.instance().getDataFolder() + "/players/" + p.getUniqueId().toString() + ".yml"));

		List<String> storedNames = new ArrayList<>();

		if (yaml.contains("stored-names")) {
			storedNames = yaml.getStringList("stored-names");
			if (!storedNames.contains(p.getName()))
				storedNames.add(p.getName());
		} else
			storedNames.add(p.getName());

		yaml.set("stored-names", storedNames);

		return yaml;
	}

	public static boolean hasFileInJar(String jarPath, EJPlugin from) {
		return from.getClass().getResourceAsStream("/" + jarPath) != null;
	}

	private static void exportFile(String jarPath, EJPlugin from, String toFolder) {
		exportFile(jarPath, from, new File(toFolder));
	}

	public static void exportFile(String jarPath, EJPlugin from, File toFolder) {
		if (from.getClass().getResourceAsStream("/" + jarPath) != null) {
			final String[] split = jarPath.split("/");

			final File toFile = new File(toFolder.getPath(), split[split.length-1]);
			if (toFile.exists()) return;

			try (
					FileOutputStream out = new FileOutputStream(toFile);
					InputStream in = from.getClass().getResourceAsStream("/" + jarPath)
					) {
				if (!toFile.exists())
					toFile.createNewFile();

				final byte[] buffer = new byte[1024];

				int read;
				while ((read = in.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}

				EText.fine("Default file " + split[split.length-1] + " was exported!");
			} catch (final IOException e) {
				e.printStackTrace();
				return;
			}

		} else EText.warn("File " + jarPath + " does not exist in jar file, Skipped");
	}

}
