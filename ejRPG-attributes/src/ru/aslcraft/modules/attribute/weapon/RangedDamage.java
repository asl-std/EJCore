package ru.aslcraft.modules.attribute.weapon;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import ru.aslcraft.api.bukkit.events.combat.CombatEvent;
import ru.aslcraft.api.bukkit.events.combat.CombatEvent.CombatType;
import ru.aslcraft.api.ejcore.entity.EPlayer;
import ru.aslcraft.core.Core;
import ru.aslcraft.modules.MAttributes;
import ru.aslcraft.modules.attribute.AttrType;
import ru.aslcraft.modules.attribute.BasicAttr;
import ru.aslcraft.modules.attribute.ListeningCombat;
import ru.aslcraft.modules.player.PlayerUtils;

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

		if (e.getReceiver().getType() == EntityType.PLAYER)
			statDef = PlayerUtils.getStatValue(receiver, MAttributes.getWeaponAttributes().getByKey("PROJECTILE_DEFENCE"))[0];

		if (e.getAttacker().getType() == EntityType.PLAYER)
			statDmg = (Core.getCfg().getBoolean("use-vanilla-damage", false, true) ? statDmg : 0) + PlayerUtils.getDamage(attacker,this);

		if (e.getType() == CombatType.PLAYER_TO_PLAYER) {
			statDmg = statDmg*(PlayerUtils.getStatValue(attacker, MAttributes.getWeaponAttributes().getByKey("PVP_DAMAGE_MODIFIER"))[0]/100);
			statDef = statDef*(PlayerUtils.getStatValue(receiver, MAttributes.getWeaponAttributes().getByKey("PVP_DEFENCE_MODIFIER"))[0]/100);
		}

		if (e.getType() == CombatType.PLAYER_TO_ENTITY)
			statDmg = statDmg*(PlayerUtils.getStatValue(attacker, MAttributes.getWeaponAttributes().getByKey("PVE_DAMAGE_MODIFIER"))[0]/100);

		if (e.getType() == CombatType.ENTITY_TO_PLAYER)
			statDef = statDef*(PlayerUtils.getStatValue(receiver, MAttributes.getWeaponAttributes().getByKey("PVE_DEFENCE_MODIFIER"))[0]/100);

		e.setDamage(statDmg*(1-0.00675*statDef/(1+0.00675*statDef)));
	}

}
