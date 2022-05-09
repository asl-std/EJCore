package ru.asl.modules.attributes;

import ru.asl.api.bukkit.events.combat.CombatEvent;

public interface ListeningCombat {

	void listen(CombatEvent e);

}
