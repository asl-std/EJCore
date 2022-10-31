package org.aslstd.modules.attribute.weapon;

import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.modules.attribute.BasicAttr;
import org.aslstd.modules.attribute.ListeningCombat;

/**
 * <p>Stun class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class Stun extends BasicAttr implements ListeningCombat {

	/**
	 * <p>Constructor for Stun.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 */
	public Stun(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	/** {@inheritDoc} */
	@Override
	public void listen(CombatEvent e) {
	}

}
