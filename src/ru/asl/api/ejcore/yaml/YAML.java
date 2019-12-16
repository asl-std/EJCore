package ru.asl.api.ejcore.yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * Basic wrapper for default .yaml files
 */
public class YAML {

	protected YamlConfiguration	yaml	= new YamlConfiguration();
	protected File				file;

	public YAML(File file) {
		this.file = file;
		if (fileExists()) try {
			load();
		} catch (IOException | InvalidConfigurationException e) {
			file = null;
			yaml = null;
			e.printStackTrace();
		}
	}

	public YAML(String path) {
		this(new File(path));
	}

	public File getFile() { return file; }

	public void load() throws FileNotFoundException, IOException, InvalidConfigurationException { yaml.load(file); }
	public void save() throws IOException { yaml.save(file); }

	protected boolean fileExists() { return file.exists(); }

	public void reload() {
		try {
			save();
			load();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void set(String path, Object value) {
		yaml.set(path, value);
		reload();
	}

	public boolean contains(String path) {
		return yaml.contains(path);
	}

	public ConfigurationSection getSection(String section) {
		return yaml.getConfigurationSection(section);
	}

	public Set<String> getKeys(boolean deep) {
		return yaml.getKeys(deep);
	}

	public int getInt(String path) {
		long request = this.getLong(path);
		return request <= Integer.MIN_VALUE ? Integer.MIN_VALUE : request >= Integer.MAX_VALUE ? Integer.MAX_VALUE : Long.valueOf(request).shortValue();
	}

	public short getShort(String path) {
		long request = this.getLong(path);
		return request <= Short.MIN_VALUE ? Short.MIN_VALUE : request >= Short.MAX_VALUE ? Short.MAX_VALUE : Long.valueOf(request).shortValue();
	}

	public long getLong(String path) {
		return yaml.getLong(path);
	}

	public double getDouble(String path) {
		return yaml.getDouble(path);
	}

	public float getFloat(String path) {
		double request = this.getDouble(path);
		return request <= Float.MIN_VALUE ? Float.MIN_VALUE : request >= Float.MAX_VALUE ? Float.MAX_VALUE : Double.valueOf(request).floatValue();
	}

	public String getString(String path) {
		return yaml.getString(path);
	}

	public boolean getBoolean(String path) {
		return yaml.getBoolean(path);
	}

	public int getInt(String path, int def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getInt(path);
	}

	public short getShort(String path, short def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getShort(path);
	}

	public long getLong(String path, long def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getLong(path);
	}

	public double getDouble(String path, double def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getDouble(path);
	}

	public float getFloat(String path, float def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getFloat(path);
	}

	public String getString(String path, String def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return this.getString(path);
	}

	public boolean getBoolean(String path, boolean def, boolean restore) {
		if (restore) if (this.getString(path) == null) set(path, def);
		return yaml.getBoolean(path);
	}

	public List<Long>    getLongList(String path) { return yaml.getLongList(path); }
	public List<Integer> getIntList(String path) { return yaml.getIntegerList(path); }
	public List<Short>   getShortList(String path) { return yaml.getShortList(path); }
	public List<Double>  getDoubleList(String path) { return yaml.getDoubleList(path); }
	public List<Float>   getFloatList(String path) { return yaml.getFloatList(path); }
	public List<String>  getStringList(String path) { return yaml.getStringList(path); }

	public static YAML getPlayerFile(Player p) {
		File file = new File("plugins/ejCore/players/" + p.getUniqueId().toString() + ".yml");
		File folder = new File("plugins/ejCore/players/");
		if (!file.exists()) try {
			folder.mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		YAML yaml = new YAML(file);

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

	public static String getFileExtension(File file) {
		String fileName = file.getName();

		if (fileName.lastIndexOf(".") > 0) return fileName.substring(fileName.lastIndexOf(".") + 1);
		else return "";
	}

}
