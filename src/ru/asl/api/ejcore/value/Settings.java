package ru.asl.api.ejcore.value;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.yaml.YAML;
import ru.asl.core.Core;

/**
 * <p>Settings class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Settings<T> {
	public ConcurrentMap<String, T> settings = new ConcurrentHashMap<>();

	protected ConcurrentMap<String, List<Consumer<T>>> binds = new ConcurrentHashMap<>();

	/**
	 * <p>getSettingsSize.</p>
	 *
	 * @return a int
	 */
	public int getSettingsSize() {
		return this.settings.size();
	}

	/**
	 * <p>addBind.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param func a {@link java.util.function.Consumer} object
	 */
	public void addBind(String key, Consumer<T> func) {
		if (key == null || func == null) {
			EText.warn("Tried to add bind using null key/function");
			return;
		}

		List<Consumer<T>> list = new ArrayList<>();
		if (hasBind(key))
			list = getBinds(key);

		list.add(func);
		this.binds.put(key, list);
	}

	/**
	 * <p>hasBind.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean hasBind(String key) {
		return (this.binds.containsKey(key) && this.binds.get(key) != null);
	}

	/**
	 * <p>Getter for the field <code>binds</code>.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public List<Consumer<T>> getBinds(String key) {
		if (hasBind(key))
			return this.binds.get(key);
		return null;
	}

	/**
	 * <p>acceptBind.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param value a T object
	 */
	public void acceptBind(String key, T value) {
		if (hasBind(key)) {
			final List<Consumer<T>> binds = getBinds(key);
			if (binds != null)
				for (final Consumer<T> bind : binds)
					bind.accept(value);
		}
	}

	/**
	 * <p>hasKey.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean hasKey(String key) {
		for (final String k : this.settings.keySet()) {
			if (k.equals(key))
				return true;
		}
		return false;
	}

	/**
	 * <p>setValue.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param value a T object
	 */
	public void setValue(String key, T value) {
		this.settings.put(key, value);
		acceptBind(key, value);
	}

	/**
	 * <p>getValue.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a T object
	 */
	public T getValue(String key) {
		return this.settings.get(key);
	}

	/**
	 * <p>hasValue.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean hasValue(String key) {
		return (this.settings.containsKey(key) && this.settings.get(key) != null);
	}

	/**
	 * <p>remove.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 */
	public void remove(String key) {
		if (hasKey(key))
			this.settings.remove(key);
	}

	/**
	 * <p>getKeys.</p>
	 *
	 * @return a {@link java.util.Set} object
	 */
	public Set<Map.Entry<String, T>> getKeys() {
		return this.settings.entrySet();
	}

	/**
	 * <p>removeKey.</p>
	 *
	 * @param keyPart a {@link java.lang.String} object
	 */
	public void removeKey(String keyPart) {
		for (final Map.Entry<String, T> entry : this.settings.entrySet()) {
			if (entry.getKey().contains(keyPart))
				remove(entry.getKey());
		}
	}

	/**
	 * <p>getKey.</p>
	 *
	 * @param keyPart a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public List<Map.Entry<String, T>> getKey(String keyPart) {
		final List<Map.Entry<String, T>> list = new LinkedList<>();
		for (final Map.Entry<String, T> entry : this.settings.entrySet()) {
			if (entry.getKey().contains(keyPart))
				list.add(entry);
		}
		return list;
	}

	/**
	 * <p>dumpToFile.</p>
	 */
	public void dumpToFile() {
		final YAML dump = new YAML(new File(Core.instance().getDataFolder() + "/dump." + System.currentTimeMillis() + "." + toString() + ".yml"));

		for (final Map.Entry<String, T> entry : this.settings.entrySet()) {
			if (entry.getKey() == null || entry.getKey().equalsIgnoreCase(""))
				continue;
			dump.set(entry.getKey(), entry.getValue());
		}

	}

	/**
	 * <p>dumpToConsole.</p>
	 */
	public void dumpToConsole() {
		for (final Map.Entry<String, T> entry : this.settings.entrySet())
			EText.warn(String.valueOf(entry.getKey()) + ": &a" + entry.getValue());
	}
}
