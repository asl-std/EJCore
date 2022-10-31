package org.aslstd.modules.attribute.weapon;

import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.modules.attribute.AttrType;
import org.aslstd.modules.attribute.BasicAttr;
import org.aslstd.modules.attribute.ListeningCombat;

/**
 * <p>MagicalDamage class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class MagicalDamage extends BasicAttr implements ListeningCombat {

	/**
	 * <p>Constructor for MagicalDamage.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 */
	public MagicalDamage(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
		type = AttrType.RANGE;
	}

	/** {@inheritDoc} */
	@Override
	public void listen(CombatEvent e) {
		/*double statDmg = e.getDamage();
		double statDef = 0;

		if (e.getReceiver().getType() == EntityType.PLAYER)
			statDef = ((EPlayer)EPlayer.getEJPlayer((Player)e.getReceiver())).getStatValue(StatManager.MAGICAL_DAMAGE)[0];

		if (e.getAttacker().getType() == EntityType.PLAYER)
			statDmg = ((EPlayer)EPlayer.getEJPlayer((Player)e.getAttacker())).getDamage(StatManager.MAGICAL_DEFENCE);

		if (e.getType() == CombatType.PLAYER_TO_PLAYER)
			statDmg = statDmg*(((EPlayer)EPlayer.getEJPlayer((Player)e.getAttacker())).getStatValue(StatManager.PVP_DAMAGE_MODIFIER)[0]/100);
		else
			if (e.getType() == CombatType.PLAYER_TO_ENTITY)
				statDmg = statDmg*(((EPlayer)EPlayer.getEJPlayer((Player)e.getAttacker())).getStatValue(StatManager.PVE_DAMAGE_MODIFIER)[0]/100);

		e.setDamage(statDmg*(1-0.00675*statDef/(1+(0.00675*statDef))));*/
	}

}
