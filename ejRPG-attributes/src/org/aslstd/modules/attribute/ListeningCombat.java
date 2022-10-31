package org.aslstd.modules.attribute;

import org.aslstd.api.bukkit.events.combat.CombatEvent;

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
	 * @param e a {@link org.aslstd.api.bukkit.events.combat.CombatEvent} object
	 */
	void listen(CombatEvent e);

}
