package ru.asl.core.configs;

import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.api.ejcore.yaml.EJConf;

public class LangConfig extends EJConf {

	public String NAME_DURABILITY, DURABILITY_SUFFIX_COLOR_DECORATOR;
	public String ERR_CONSOLE, ERR_NO_PERMISSION, ERR_ARG_CANT_BE_NULL, ERR_PLAYER_ONLINE;

	public LangConfig(String fileName, EJPlugin plugin) {
		super(fileName, plugin);
	}

	@Override
	public void loadConfig() {
		ERR_CONSOLE = this.getString("err.console-error", "&4Join to server for using this command.", true);
		ERR_NO_PERMISSION = this.getString("err.no-permission", "&4You don't have permission to do this.", true);
		ERR_ARG_CANT_BE_NULL = this.getString("err.arg-null", "&4Args can't be null", true);
		ERR_PLAYER_ONLINE = this.getString("err.player-online", "&4Offline player can't be used in commands", true);

		NAME_DURABILITY = this.getString("util.durability", "&7Durability", true);
		DURABILITY_SUFFIX_COLOR_DECORATOR = this.getString("util.durability-suffix-color-decorator", "&7", true);
	}

}
