package org.aslstd.modules.attribute.weapon;

import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.api.bukkit.events.combat.CombatEvent.CombatType;
import org.aslstd.modules.attribute.BasicAttr;
import org.aslstd.modules.attribute.ListeningCombat;
import org.aslstd.modules.player.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * <p>Knockback class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class Knockback extends BasicAttr implements ListeningCombat {

	/**
	 * <p>Constructor for Knockback.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 */
	public Knockback(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	/** {@inheritDoc} */
	@Override
	public void listen(CombatEvent e) {
		if (e.getType() != CombatType.PLAYER_TO_ENTITY && e.getType() != CombatType.PLAYER_TO_PLAYER) return;
		final EPlayer rpg = EPlayer.getEPlayer((Player)e.getAttacker());

		final Vector damagerToEntityVec = e.getReceiver().getLocation().toVector().subtract(e.getAttacker().getLocation().toVector()).normalize();

		e.getReceiver().setVelocity(damagerToEntityVec.multiply(0.025 * PlayerUtils.getStatValue(rpg, this)[0]));
	}
}
