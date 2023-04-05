package org.aslstd.api.attributes.weapon;

import org.aslstd.api.attributes.BasicAttr;
import org.aslstd.api.attributes.ListeningCombat;
import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.api.bukkit.events.combat.CombatEvent.CombatType;
import org.aslstd.api.bukkit.value.util.NumUtil;
import org.bukkit.entity.Player;

/**
 * <p>Dodge class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class Dodge extends BasicAttr implements ListeningCombat {

	/**
	 * <p>Constructor for Dodge.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param base a double
	 * @param perLevel a double
	 */
	public Dodge(String keyName, String path, double base, double perLevel) {
		super(keyName, path, base, perLevel);
	}

	/** {@inheritDoc} */
	@Override
	public void listen(CombatEvent e) {
		if (e.getType() != CombatType.ENTITY_TO_PLAYER && e.getType() != CombatType.PLAYER_TO_PLAYER) return;
		final EPlayer rpg = EPlayer.getEPlayer((Player)e.getReceiver());

		final double absValue = rpg.getStatValue(this)[0];

		if (NumUtil.isTrue(absValue, 100)) {
			e.setCancelled(true);
		}
	}

}
