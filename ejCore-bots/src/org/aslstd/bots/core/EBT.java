package org.aslstd.bots.core;

import java.io.File;

import org.aslstd.api.bukkit.yaml.YAML;
import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.bots.core.config.GConfig;
import org.aslstd.bots.core.discord.DSBot;
import org.aslstd.bots.core.vk.VKBot;
import org.aslstd.core.Core;

import lombok.Getter;

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
