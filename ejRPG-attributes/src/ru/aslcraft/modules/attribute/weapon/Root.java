package ru.aslcraft.modules.attribute.weapon;

import ru.aslcraft.api.bukkit.events.combat.CombatEvent;
import ru.aslcraft.modules.attribute.BasicAttr;
import ru.aslcraft.modules.attribute.ListeningCombat;

/**
 * <p>Root class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class Root extends BasicAttr implements ListeningCombat {

	/**
	 * <p>Constructor for Root.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 */
	public Root(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	/** {@inheritDoc} */
	@Override
	public void listen(CombatEvent e) {
	}

}
