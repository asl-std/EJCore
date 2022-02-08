package ru.asl.api.ejcore.module;

import java.util.List;

import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.api.ejcore.yaml.YAML;
import ru.asl.core.Core;

public interface EJModule {

	EJPlugin CORE_PLUGIN = Core.instance();

	YAML getModuleConfig();

	String getModuleName();

	String getModuleVersion();

	List<String> getUncompatibleModules();

	void loadModule();

	void reloadModule();

}
