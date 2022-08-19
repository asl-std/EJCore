package ru.aslcraft.api.bukkit.yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import ru.aslcraft.api.bukkit.message.EText;

/**
 * Basic wrapper for default .yml files
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class YAML {

	@Getter private static final YAML mainConfig;

	static {

		final JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("ejCore");

		if (plugin != null)
			mainConfig = new YAML("plugins/ejCore/config.yml");
		else
			mainConfig = null;
	}

	protected YamlConfiguration	yaml	= new YamlConfiguration();
	protected File				file;

	/**
	 * <p>Constructor for YAML.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @param plugin a {@link ru.aslcraft.api.bukkit.plugin.EJPlugin} object
	 * @param extendedPath a {@link java.lang.String} object
	 */
	public YAML(File file, JavaPlugin plugin, String extendedPath) {
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

	/**
	 * <p>Constructor for YAML.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @param plugin a {@link ru.aslcraft.api.bukkit.plugin.EJPlugin} object
	 */
	public YAML(String path, JavaPlugin plugin) { this(new File(path), plugin, null); }

	/**
	 * <p>Constructor for YAML.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 */
	public YAML(String path) { this(new File(path), null, null); }

	/**
	 * <p>Constructor for YAML.</p>
	 *
	 * @param file a {@link java.io.File} object
	 */
	public YAML(File file) { this(file, null, null); }

	/**
	 * <p>contains.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean contains(String path) {
		return yaml.contains(path);
	}

	/**
	 * <p>fileExists.</p>
	 *
	 * @return a boolean
	 */
	protected boolean fileExists() { return file.exists(); }
	/**
	 * <p>getBoolean.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean getBoolean(String path) {
		return yaml.getBoolean(path);
	}

	/**
	 * <p>getBoolean.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @param def a boolean
	 * @param restore a boolean
	 * @return a boolean
	 */
	public boolean getBoolean(String path, boolean def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return yaml.getBoolean(path);
	}

	/**
	 * <p>getDouble.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a double
	 */
	public double getDouble(String path) {
		return yaml.getDouble(path);
	}

	/**
	 * <p>getDouble.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @param def a double
	 * @param restore a boolean
	 * @return a double
	 */
	public double getDouble(String path, double def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getDouble(path);
	}

	/**
	 * <p>getDoubleList.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public List<Double>  getDoubleList(String path) { return yaml.getDoubleList(path); }

	/**
	 * <p>Getter for the field <code>file</code>.</p>
	 *
	 * @return a {@link java.io.File} object
	 */
	public File getFile() { return file; }

	/**
	 * <p>getFloat.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a float
	 */
	public float getFloat(String path) {
		final double request = this.getDouble(path);
		return request <= Float.MIN_VALUE ? Float.MIN_VALUE : request >= Float.MAX_VALUE ? Float.MAX_VALUE : Double.valueOf(request).floatValue();
	}

	/**
	 * <p>getFloat.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @param def a float
	 * @param restore a boolean
	 * @return a float
	 */
	public float getFloat(String path, float def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getFloat(path);
	}

	/**
	 * <p>getFloatList.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public List<Float>   getFloatList(String path) { return yaml.getFloatList(path); }

	/**
	 * <p>getInt.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a int
	 */
	public int getInt(String path) {
		final long request = this.getLong(path);
		return request <= Integer.MIN_VALUE ? Integer.MIN_VALUE : request >= Integer.MAX_VALUE ? Integer.MAX_VALUE : Long.valueOf(request).shortValue();
	}

	/**
	 * <p>getInt.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @param def a int
	 * @param restore a boolean
	 * @return a int
	 */
	public int getInt(String path, int def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getInt(path);
	}

	/**
	 * <p>getIntList.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public List<Integer> getIntList(String path) { return yaml.getIntegerList(path); }

	/**
	 * <p>getKeys.</p>
	 *
	 * @param deep a boolean
	 * @return a {@link java.util.Set} object
	 */
	public Set<String> getKeys(boolean deep) {
		return yaml.getKeys(deep);
	}

	/**
	 * <p>getLong.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a long
	 */
	public long getLong(String path) {
		return yaml.getLong(path);
	}

	/**
	 * <p>getLong.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @param def a long
	 * @param restore a boolean
	 * @return a long
	 */
	public long getLong(String path, long def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getLong(path);
	}

	/**
	 * <p>getLongList.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public List<Long>    getLongList(String path) { return yaml.getLongList(path); }

	/**
	 * <p>getSection.</p>
	 *
	 * @param section a {@link java.lang.String} object
	 * @return a {@link org.bukkit.configuration.ConfigurationSection} object
	 */
	public ConfigurationSection getSection(String section) {
		return yaml.getConfigurationSection(section);
	}

	/**
	 * <p>getShort.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a short
	 */
	public short getShort(String path) {
		final long request = this.getLong(path);
		return request <= Short.MIN_VALUE ? Short.MIN_VALUE : request >= Short.MAX_VALUE ? Short.MAX_VALUE : Long.valueOf(request).shortValue();
	}

	/**
	 * <p>getShort.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @param def a short
	 * @param restore a boolean
	 * @return a short
	 */
	public short getShort(String path, short def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getShort(path);
	}

	/**
	 * <p>getShortList.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public List<Short> getShortList(String path) { return yaml.getShortList(path); }

	/**
	 * <p>getString.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public String getString(String path) {
		return yaml.getString(path);
	}

	/**
	 * <p>getString.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @param def a {@link java.lang.String} object
	 * @param restore a boolean
	 * @return a {@link java.lang.String} object
	 */
	public String getString(String path, String def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getString(path);
	}
	/**
	 * <p>getStringList.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public List<String>  getStringList(String path) {
		return yaml.getStringList(path);
	}
	/**
	 * <p>load.</p>
	 *
	 * @throws java.io.FileNotFoundException if any.
	 * @throws java.io.IOException if any.
	 * @throws org.bukkit.configuration.InvalidConfigurationException if any.
	 */
	public void load() throws FileNotFoundException, IOException, InvalidConfigurationException { yaml.load(file); }
	/**
	 * <p>reload.</p>
	 */
	public void reload() {
		try {
			load();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * <p>save.</p>
	 */
	public void save() { try { yaml.save(file); } catch (final IOException e) { e.printStackTrace(); } }
	/**
	 * <p>set.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @param value a {@link java.lang.Object} object
	 */
	public void set(String path, Object value) {
		yaml.set(path, value);
		save();
	}

	/**
	 * <p>getFileExtension.</p>
	 *
	 * @param file a {@link java.io.File} object
	 * @return a {@link java.lang.String} object
	 */
	public static String getFileExtension(File file) {
		final String fileName = file.getName();

		if (fileName.lastIndexOf(".") > 0) return fileName.substring(fileName.lastIndexOf(".") + 1);
		else return "";
	}

	/**
	 * <p>getPlayerFile.</p>
	 *
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @return a {@link ru.aslcraft.api.ejcore.yaml.YAML} object
	 */
	public static YAML getPlayerFile(OfflinePlayer p) {
		final YAML yaml = new YAML(new File("plugins/ejCore/players/" + p.getUniqueId().toString() + ".yml"));

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

	public static YAML getCustomStorage(String path) {
		return new YAML(new File("plugins/ejCore/data/" + path + ".yml"));
	}

	public static YAML of(String path, JavaPlugin plugin) {
		return new YAML(path, plugin);
	}

	public static YAML of(String path) {
		final JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("ejCore");

		if (plugin != null && plugin.isEnabled())
			return of(plugin.getDataFolder() + "/" + path, plugin);
		else
			return new YAML(path);
	}

	/**
	 * <p>hasFileInJar.</p>
	 *
	 * @param jarPath a {@link java.lang.String} object
	 * @param from a {@link ru.aslcraft.api.bukkit.plugin.EJPlugin} object
	 * @return a boolean
	 */
	public static boolean hasFileInJar(String jarPath, JavaPlugin from) {
		return from.getClass().getResourceAsStream("/" + jarPath) != null;
	}

	private static void exportFile(String jarPath, JavaPlugin from, String toFolder) {
		exportFile(jarPath, from, new File(toFolder));
	}

	/**
	 * <p>exportFile.</p>
	 *
	 * @param jarPath a {@link java.lang.String} object
	 * @param from a {@link ru.aslcraft.api.bukkit.plugin.EJPlugin} object
	 * @param toFolder a {@link java.io.File} object
	 */
	public static void exportFile(String jarPath, JavaPlugin from, File toFolder) {
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
