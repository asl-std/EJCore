package ru.asl.api.incubator.item;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <p>TextReaderWriter class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class TextReaderWriter {

	/**
	 * <p>write.</p>
	 *
	 * @param list a {@link java.lang.Class} object
	 * @param toFile a {@link java.io.File} object
	 */
	public static void write(Class<? extends Enum<?>> list, File toFile) {
		try(final BufferedWriter fw = new BufferedWriter (new FileWriter(toFile));) {
			final Map<String, String> mat = new ConcurrentHashMap<>();
			final List<String> legacy = new ArrayList<>();
			for (final Enum<?> obj : list.getEnumConstants()) {
				if (obj.name().contains("LEGACY")) {
					legacy.add(obj.name().replaceAll("LEGACY_", ""));
					continue;
				}

				mat.put(obj.name(), obj.name());
			}

			for (final String leg : legacy) {
				int storedDistance = leg.length();
				String storedKey = leg;

				for (final String key : mat.keySet()) {
					final int levenshein = levenshtein(leg, key, false);
					if (storedDistance < levenshein) {
						storedDistance = levenshein;
						storedKey = key;
					}
				}

				if (storedDistance < leg.length())
					mat.put(storedKey, leg);
			}


			for (final Entry<String,String> entry : mat.entrySet()) {
				fw.write(entry.getKey() + "(" + entry.getValue() + ");");
				fw.newLine();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>levenshtein.</p>
	 *
	 * @param stringOne a {@link java.lang.String} object
	 * @param stringTwo a {@link java.lang.String} object
	 * @param caseSensitive a boolean
	 * @return a int
	 */
	public static int levenshtein(String stringOne, String stringTwo, boolean caseSensitive) {
		// if we want to ignore case sensitivity, lower case the strings
		if (!caseSensitive) {
			stringOne = stringOne.toLowerCase();
			stringTwo = stringTwo.toLowerCase();
		}

		// store length
		final int m = stringOne.length();
		final int n = stringTwo.length();

		// matrix to store differences
		final int[][] deltaM = new int[m + 1][n + 1];

		for (int i = 1; i <= m; i++)
			deltaM[i][0] = i;

		for (int j = 1; j <= n; j++)
			deltaM[0][j] = j;

		for (int j = 1; j <= n; j++) {
			for (int i = 1; i <= m; i++) {
				if (stringOne.charAt(i - 1) == stringTwo.charAt(j - 1))
					deltaM[i][j] = deltaM[i - 1][j - 1];
				else
					deltaM[i][j] = Math.min(deltaM[i - 1][j] + 1,
							Math.min(deltaM[i][j - 1] + 1, deltaM[i - 1][j - 1] + 1));
			}
		}

		return deltaM[m][n];

	}

}
