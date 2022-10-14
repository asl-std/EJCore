package ru.aslcraft.api.language;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.entity.Player;

import ru.aslcraft.api.bukkit.message.EText;

public class LangAPI {

	public static final LangAPI INSTANCE = new LangAPI();

	private static List<Locale> locales = Arrays.asList(SimpleDateFormat.getAvailableLocales());

	private static final ConcurrentMap<String, LocaleHolder> MESSAGES = new ConcurrentHashMap<>();

	private static final String DEF = "en_us";

	public static void sendMessage(Player p, String key) { send(p, key, false); }

	public static void sendMessageColored(Player p, String key) { send(p, key, true); }

	private static void send(Player p, String key, boolean colored) {
		LocaleHolder hold;

		if (MESSAGES.containsKey(p.getLocale()))
			hold = MESSAGES.get(p.getLocale());
		else
			hold = MESSAGES.get(DEF);

		if (hold.getMessage(key) == null) return;

		EText.send(p, hold.getMessage(key), colored);
	}

	private static boolean isValidLocale(String locale) {
		for (final Locale loc : locales)
			if (loc.equals(Locale.forLanguageTag(locale)))
				return true;
		return false;
	}

	private LangAPI() {}

	protected void collectFile(LocaleFile file) {
		if (!isValidLocale(file.locale)) return;

		LocaleHolder holder;

		if (MESSAGES.containsKey(file.locale))
			holder = MESSAGES.get(file.locale);
		else {
			holder = new LocaleHolder();
			MESSAGES.put(file.locale, holder);
		}

		for (final Entry<String, String> entry : file.collectLocale().entrySet())
			holder.putMessage(entry.getKey(), entry.getValue());
	}

}
