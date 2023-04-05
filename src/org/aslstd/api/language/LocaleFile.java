package org.aslstd.api.language;

import java.util.Map;

import org.aslstd.api.bukkit.yaml.EJConf;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class LocaleFile extends EJConf {

	protected String locale;

	public abstract Map<String,String> collectLocale();

	public LocaleFile(String locale, JavaPlugin plugin) {
		super(locale.toLowerCase() + ".lang", plugin);
		this.locale = locale.toLowerCase();
		LangAPI.INSTANCE.collectFile(this);
	}

	@Override
	public void reload() {
		super.reload();
		LangAPI.INSTANCE.collectFile(this);
	}

}
