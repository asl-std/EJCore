package ru.asl.api.ejcore.value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;

import lombok.Getter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.yaml.YAML;

/**
 * <p>Abstract CustomParam class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public abstract class CustomParam {

	@Getter private final String key;
	@Getter private final String visualName;

	@Getter private final Pattern pattern;

	/**
	 * <p>isAllowedValue.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a boolean
	 */
	protected abstract boolean isAllowedValue(String value);

	/**
	 * <p>Constructor for CustomParam.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param file a {@link ru.asl.api.ejcore.yaml.YAML} object
	 */
	public CustomParam(String key, YAML file) {
		this.key = key;
		visualName = file.getString("eimodule.util." + key, "&7" + WordUtils.capitalizeFully(toString()), true);

		pattern = Pattern.compile(EText.e(visualName.toLowerCase()) + ":\\s*([\\wa-zA-Zа-я-А-Я]*)");
	}

	/**
	 * <p>getValue.</p>
	 *
	 * @param from a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public final String getValue(String from) {
		String val = null;

		final Matcher match = pattern.matcher(EText.e(from).toLowerCase());

		if (match.find())
			val = match.group(1);

		return val;
	}

	/**
	 * <p>convert.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public final String convert(String value) {
		if (!isAllowedValue(value)) return null;

		return EText.c(visualName + ": " + value);
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return key.replaceAll("_", "-");
	}

}
