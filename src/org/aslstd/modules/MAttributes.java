package org.aslstd.modules;

import java.util.Collections;
import java.util.List;

import org.aslstd.api.ejcore.module.EJAddon;
import org.aslstd.core.listeners.RegisterEventListener;
import org.aslstd.modules.attribute.managers.WAttributes;
import org.aslstd.modules.listener.CombatListener;
import org.aslstd.modules.player.PlayerLoader;

import lombok.Getter;

public class MAttributes extends EJAddon {

	@Override
	public String getModuleName() {
		return "ejRPG-Attributes";
	}

	@Override
	public String getModuleVersion() {
		return "1.0.0-SNAPSHOT";
	}

	@Override
	public List<String> getUncompatibleModules() {
		return Collections.emptyList();
	}

	@Getter private static MAttributes instance;

	@Getter private static PlayerLoader playerLoader;
	@Getter private static WAttributes weaponAttributes;

	@Override
	public void loadModule() {
		playerLoader = new PlayerLoader();

		weaponAttributes = new WAttributes();

		RegisterEventListener.unregister("combatEventCustom");
		RegisterEventListener.register("combatEventCustom", new CombatListener());
		RegisterEventListener.register("module-attributes", playerLoader);
	}

}
