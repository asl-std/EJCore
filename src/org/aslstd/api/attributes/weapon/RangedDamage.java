package org.aslstd.api.attributes.weapon;

import org.aslstd.api.attributes.AttrType;
import org.aslstd.api.attributes.BasicAttr;
import org.aslstd.api.attributes.ListeningCombat;
import org.aslstd.api.attributes.managers.WAttributes;
import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.api.bukkit.events.combat.CombatEvent.CombatType;
import org.aslstd.core.Core;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * <p>RangedDamage class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class RangedDamage extends BasicAttr implements ListeningCombat {

	/**
	 * <p>Constructor for RangedDamage.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 */
	public RangedDamage(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel, AttrType.RANGE);
	}

	/** {@inheritDoc} */
	@Override
	public void listen(CombatEvent e) {
		if (!e.isRanged()) return;

		double statDmg = e.getDamage();
		double statDef = 0;

		final EPlayer receiver = e.getReceiver().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getReceiver() ) : null;
		final EPlayer attacker = e.getAttacker().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getAttacker() ) : null;

		if (receiver != null)
			statDef = receiver.getStatValue(WAttributes.getByKey("PROJECTILE_DEFENCE"))[0];

		if (attacker != null)
			statDmg = (Core.getCfg().getBoolean("use-vanilla-damage", false, true) ? statDmg : 0) + attacker.getDamage(this);

		if (e.getType() == CombatType.PLAYER_TO_PLAYER) {
			statDmg = statDmg*(attacker.getStatValue(WAttributes.getByKey("PVP_DAMAGE_MODIFIER"))[0]/100);
			statDef = statDef*(receiver.getStatValue(WAttributes.getByKey("PVP_DEFENCE_MODIFIER"))[0]/100);
		}

		if (e.getType() == CombatType.PLAYER_TO_ENTITY)
			statDmg = statDmg*(attacker.getStatValue(WAttributes.getByKey("PVE_DAMAGE_MODIFIER"))[0]/100);

		if (e.getType() == CombatType.ENTITY_TO_PLAYER)
			statDef = statDef*(receiver.getStatValue(WAttributes.getByKey("PVE_DEFENCE_MODIFIER"))[0]/100);

		e.setDamage(statDmg*(1-0.00675*statDef/(1+0.00675*statDef)));
	}

}
