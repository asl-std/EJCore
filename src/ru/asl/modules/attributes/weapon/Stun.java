package ru.asl.modules.attributes.weapon;

import ru.asl.api.bukkit.events.combat.CombatEvent;
import ru.asl.modules.attributes.BasicAttr;
import ru.asl.modules.attributes.ListeningCombat;

public final class Stun extends BasicAttr implements ListeningCombat {

	public Stun(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	@Override
	public void listen(CombatEvent e) {
	}

}
