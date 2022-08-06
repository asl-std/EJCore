package ru.aslcraft.api.varsystem.reader;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.aslcraft.api.ejcore.value.StringSettings;

/**
 * <p>VarReader class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class VarReader {

	/**
	 * <p>readLine.</p>
	 *
	 * @param line a {@link java.lang.String} object
	 * @return a {@link ru.aslcraft.api.ejcore.value.StringSettings} object
	 */
	public static StringSettings readLine(String line) {
		final StringSettings result = new StringSettings();
		final String prepaired = line.toLowerCase().replaceAll("\\s", "");
		VarTarget target = null;

		for (final VarTarget t : VarTarget.values())
			if (prepaired.startsWith(t.getKey()))
				target = t;

		if (target == null) return null;

		result.setValue("target", target.name());
		ConcurrentMap<String,String> options = null;

		if (line.contains("{"))
			options = processBrackets(line);

		if (options != null)
			for (final Entry<String, String> entry : options.entrySet()) {
				result.setValue(entry.getKey(), entry.getValue());
			}

		return result;
	}

	private static ConcurrentMap<String,String> processBrackets(String line) {
		final ConcurrentHashMap<String,String> result = new ConcurrentHashMap<>();
		final String prepaired = line.substring(line.indexOf("{"), line.lastIndexOf("}"));

		final String[] options = prepaired.split(",");

		for (final String optionLine : options) {
			if (optionLine == null) continue;

			if (!optionLine.contains("="))
				result.put(optionLine, optionLine);
			else {
				final String key = optionLine.split("=")[0];
				final String value = optionLine.split("=")[1];

				result.put(key, value);
			}

		}

		return result;
	}

}
