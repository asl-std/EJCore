package ru.aslcraft.modules.attribute;

import ru.aslcraft.api.bukkit.events.combat.CombatEvent;

/**
 * <p>ListeningCombat interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface ListeningCombat {

	/**
	 * <p>listen.</p>
	 *
	 * @param e a {@link ru.aslcraft.api.bukkit.events.combat.CombatEvent} object
	 */
	void listen(CombatEvent e);

}
