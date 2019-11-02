package ru.asl.api.ejcore.time;

import java.util.concurrent.ConcurrentHashMap;

import ru.asl.api.ejcore.value.Settings;

public class Cooldown {

	private ConcurrentHashMap<String, Long> cooldownList = new ConcurrentHashMap<>();

	public Cooldown(Settings settings) { }

	public boolean removeCooldown(String key) {
		if (!cooldownList.containsKey(key))
			return true;
		return cooldownList.remove(key) == null;
	}

	public void addCooldown(String key, long millis) {
		if (isCooldownEnded(key))
			cooldownList.put(key, Long.valueOf(System.currentTimeMillis() + millis));
	}

	public void setCooldown(String key, long millis) {
		cooldownList.put(key, Long.valueOf(System.currentTimeMillis() + millis));
	}

	public long getCooldown(String key) {
		if (!cooldownList.containsKey(key))
			return 0l;

		return cooldownList.get(key).longValue() - System.currentTimeMillis();
	}

	public boolean isCooldownEnded(String key) {
		if (!cooldownList.containsKey(key)) return true;
		if (getCooldown(key) <= 0) {
			removeCooldown(key);
			return true;
		}
		return false;
	}

}
