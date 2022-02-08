package ru.asl.modules.playerstats;

import ru.asl.api.bukkit.events.CombatEvent;
import ru.asl.modules.playerstats.basic.BasicStat;
import ru.asl.modules.playerstats.basic.interfaze.ListeningCombat;

public final class Stun extends BasicStat implements ListeningCombat {

	public Stun(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	@Override
	public void listen(CombatEvent e) {
	}

}
