package ru.aslcraft.modules;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import ru.aslcraft.api.ejcore.module.EJAddon;
import ru.aslcraft.core.listeners.RegisterEventListener;
import ru.aslcraft.modules.attribute.managers.WeaponAttributesManager;
import ru.aslcraft.modules.listener.CombatListener;
import ru.aslcraft.modules.player.PlayerLoader;

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

	@Getter private static PlayerLoader playerLoader;
	@Getter private static WeaponAttributesManager weaponAttributes;

	@Override
	public void loadModule() {
		playerLoader = new PlayerLoader();

		weaponAttributes = new WeaponAttributesManager();

		RegisterEventListener.unregister("combatEventCustom");
		RegisterEventListener.register("combatEventCustom", new CombatListener());
		RegisterEventListener.register("module-attributes", playerLoader);
	}

}
