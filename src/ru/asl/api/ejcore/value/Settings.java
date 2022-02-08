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

public class Settings<T> {
	public ConcurrentMap<String, T> settings = new ConcurrentHashMap<>();

	protected ConcurrentMap<String, List<Consumer<T>>> binds = new ConcurrentHashMap<>();

	public int getSettingsSize() {
		return this.settings.size();
	}

	public void addBind(String key, Consumer<T> func) {
		if (key == null || func == null) {
			EText.warn("Tried to add bind using null key/function");
			return;
		}

		if (!hasKey(key)) {
			EText.warn("Tried to add bind to non existent key");
			return;
		}

		List<Consumer<T>> list = new ArrayList<>();
		if (hasBind(key))
			list = getBinds(key);

		list.add(func);
		this.binds.put(key, list);
	}

	public boolean hasBind(String key) {
		return (this.binds.containsKey(key) && this.binds.get(key) != null);
	}

	public List<Consumer<T>> getBinds(String key) {
		if (hasBind(key))
			return this.binds.get(key);
		return null;
	}

	public void acceptBind(String key, T value) {
		if (hasBind(key)) {
			final List<Consumer<T>> binds = getBinds(key);
			if (binds != null)
				for (final Consumer<T> bind : binds)
					bind.accept(value);
		}
	}

	public boolean hasKey(String key) {
		for (final String k : this.settings.keySet()) {
			if (k.equals(key))
				return true;
		}
		return false;
	}

	public void setValue(String key, T value) {
		this.settings.put(key, value);
		acceptBind(key, value);
	}

	public T getValue(String key) {
		return this.settings.get(key);
	}

	public boolean hasValue(String key) {
		return (this.settings.containsKey(key) && this.settings.get(key) != null);
	}

	public void remove(String key) {
		if (hasKey(key))
			this.settings.remove(key);
	}

	public Set<Map.Entry<String, T>> getKeys() {
		return this.settings.entrySet();
	}

	public void removeKey(String keyPart) {
		for (final Map.Entry<String, T> entry : this.settings.entrySet()) {
			if (entry.getKey().contains(keyPart))
				remove(entry.getKey());
		}
	}

	public List<Map.Entry<String, T>> getKey(String keyPart) {
		final List<Map.Entry<String, T>> list = new LinkedList<>();
		for (final Map.Entry<String, T> entry : this.settings.entrySet()) {
			if (entry.getKey().contains(keyPart))
				list.add(entry);
		}
		return list;
	}

	public void dumpToFile() {
		final YAML dump = new YAML(new File(Core.instance().getDataFolder() + "/dump." + System.currentTimeMillis() + "." + toString() + ".yml"));

		for (final Map.Entry<String, T> entry : this.settings.entrySet()) {
			if (entry.getKey() == null || entry.getKey().equalsIgnoreCase(""))
				continue;
			dump.set(entry.getKey(), entry.getValue());
		}

	}

	public void dumpToConsole() {
		for (final Map.Entry<String, T> entry : this.settings.entrySet())
			EText.warn(String.valueOf(entry.getKey()) + ": &a" + entry.getValue());
	}
}
