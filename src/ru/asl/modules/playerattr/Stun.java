package ru.asl.modules.playerattr;

import ru.asl.api.bukkit.events.CombatEvent;
import ru.asl.modules.playerattr.basic.BasicAttr;
import ru.asl.modules.playerattr.basic.interfaze.ListeningCombat;

public final class Stun extends BasicAttr implements ListeningCombat {

	public Stun(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	@Override
	public void listen(CombatEvent e) {
	}

}
