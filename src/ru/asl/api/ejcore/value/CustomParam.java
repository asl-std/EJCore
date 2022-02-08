package ru.asl.api.ejcore.value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;

import lombok.Getter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.yaml.YAML;

public abstract class CustomParam {

	@Getter private final String key;
	@Getter private final String visualName;

	private final Pattern patt;

	protected abstract boolean isAllowedValue(String value);

	public CustomParam(String key, YAML file) {
		this.key = key;
		visualName = file.getString("eimodule.util." + key, "&7" + WordUtils.capitalizeFully(toString()), true);

		patt = Pattern.compile(EText.e(visualName.toLowerCase()) + ":\\s*([\\wa-zA-Zа-я-А-Я]*)");
	}

	public final String getValue(String from) {
		String val = null;

		final Matcher match = patt.matcher(EText.e(from).toLowerCase());

		if (match.find())
			val = match.group(1);

		return val;
	}

	public final String convert(String value) {
		if (!isAllowedValue(value)) return null;

		return EText.c(visualName + ": " + value);
	}

	@Override
	public final String toString() {
		return key.replaceAll("_", "-");
	}

}
