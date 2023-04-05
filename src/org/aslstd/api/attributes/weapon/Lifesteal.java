package org.aslstd.api.attributes.weapon;

import org.aslstd.api.attributes.BasicAttr;
import org.aslstd.api.attributes.ListeningCombat;
import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.api.bukkit.events.combat.CombatEvent.CombatType;
import org.bukkit.entity.Player;

/**
 * <p>Lifesteal class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class Lifesteal extends BasicAttr implements ListeningCombat {

	/**
	 * <p>Constructor for Lifesteal.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 */
	public Lifesteal(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	/** {@inheritDoc} */
	@Override
	public void listen(CombatEvent e) {
		if (e.getType() != CombatType.PLAYER_TO_ENTITY && e.getType() != CombatType.PLAYER_TO_PLAYER) return;
		final EPlayer rpg = EPlayer.getEPlayer((Player)e.getAttacker());

		final double absValue = rpg.getStatValue(this)[0];

		rpg.heal(e.getDamage() * (absValue/100));
	}

}
