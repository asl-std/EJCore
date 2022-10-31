package ru.aslcraft.bots.core;

import java.io.File;

import lombok.Getter;
import ru.aslcraft.api.bukkit.yaml.YAML;
import ru.aslcraft.api.ejcore.plugin.EJPlugin;
import ru.aslcraft.bots.core.config.GConfig;
import ru.aslcraft.bots.core.discord.DSBot;
import ru.aslcraft.bots.core.vk.VKBot;
import ru.aslcraft.core.Core;

public class EBT extends EJPlugin {

	@Override
	public int getPriority() { return 0; }

	@Getter private static GConfig cfg;

	@Override
	public void init() {
		checkConfig();

		if(cfg.getBoolean("discord.ej-discordbot-enabled",false,true)) {
			new Thread(new DSBot()).start();
		}

		if (cfg.getBoolean("vk.ej-vkbot-enabled", false, true)){
			new Thread(new VKBot()).start();
		}
	}

	private void checkConfig() {
		final File file = new File(Core.instance().getDataFolder() + "/bots.yml");

		if (!file.exists())
			YAML.exportFile(file.getName(), this, file.getParentFile());

		cfg = new GConfig();
	}

}
