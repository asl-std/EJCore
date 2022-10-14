package ru.aslcraft.api.language;

import java.util.HashMap;
import java.util.Map;

import ru.aslcraft.api.bukkit.message.EText;

public class LocaleHolder {

	private Map<String, String> messages = new HashMap<>();

	public String getMessage(String key) {
		if (!messages.containsKey(key)) return null;
		return messages.get(key);
	}

	public String getMessageColored(String key) {
		return EText.c(getMessage(key));
	}

	protected void putMessage(String key, String value) {
		putMessage(key, value, true);
	}

	protected void putMessage(String key, String value, boolean ignore) {
		if (!ignore)
			if (messages.containsKey(key)) return;

		messages.put(key, value);
	}

}
