package ru.aslcraft.modules.attribute.weapon;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import ru.aslcraft.api.bukkit.events.combat.CombatEvent;
import ru.aslcraft.api.bukkit.events.combat.CombatEvent.CombatType;
import ru.aslcraft.api.ejcore.entity.EPlayer;
import ru.aslcraft.api.ejcore.utils.ServerVersion;
import ru.aslcraft.core.Core;
import ru.aslcraft.modules.MAttributes;
import ru.aslcraft.modules.attribute.AttrType;
import ru.aslcraft.modules.attribute.BasicAttr;
import ru.aslcraft.modules.attribute.ListeningCombat;
import ru.aslcraft.modules.player.PlayerUtils;

/**
 * <p>PhysicalDamage class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class PhysicalDamage extends BasicAttr implements ListeningCombat {

	/**
	 * <p>Constructor for PhysicalDamage.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 */
	public PhysicalDamage(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel, AttrType.RANGE);
	}

	/** {@inheritDoc} */
	@SuppressWarnings({ "null" })
	@Override
	public void listen(CombatEvent e) {
		if (e.isRanged()) return;

		double fistDmg = 0;
		double vanillaDmg = e.getDamage();
		double statDmg = 0;
		double statDef = 0;

		final EPlayer receiver = e.getReceiver().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getReceiver() ) : null;
		final EPlayer attacker = e.getAttacker().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getAttacker() ) : null;

		if (e.getReceiver().getType() == EntityType.PLAYER)
			statDef = PlayerUtils.getStatValue(receiver, MAttributes.getWeaponAttributes().getByKey("PHYSICAL_DEFENCE"))[0];

		if (e.getAttacker().getType() == EntityType.PLAYER) {
			statDmg = PlayerUtils.getDamage(attacker, this);

			if (!Core.getCfg().getBoolean("use-vanilla-damage", false, true))
				vanillaDmg = 0;

			if (MAttributes.getWeaponAttributes().getByKey("FIST_DAMAGE").isEnabled())
				fistDmg = PlayerUtils.getDamage(attacker, MAttributes.getWeaponAttributes().getByKey("FIST_DAMAGE"));
		}

		if (e.getType() == CombatType.PLAYER_TO_PLAYER) {
			statDmg = statDmg*(PlayerUtils.getStatValue(attacker, MAttributes.getWeaponAttributes().getByKey("PVP_DAMAGE_MODIFIER"))[0]/100);
			statDef = statDef*(PlayerUtils.getStatValue(receiver, MAttributes.getWeaponAttributes().getByKey("PVP_DEFENCE_MODIFIER"))[0]/100);
		}

		if (e.getType() == CombatType.PLAYER_TO_ENTITY)
			statDmg = statDmg*(PlayerUtils.getStatValue(attacker, MAttributes.getWeaponAttributes().getByKey("PVE_DAMAGE_MODIFIER"))[0]/100);

		if (e.getType() == CombatType.ENTITY_TO_PLAYER)
			statDef = statDef*(PlayerUtils.getStatValue(receiver, MAttributes.getWeaponAttributes().getByKey("PVE_DEFENCE_MODIFIER"))[0]/100);

		double summdmg = (fistDmg+statDmg+vanillaDmg);

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_15_2))
			if (e.getAttacker().getType() == EntityType.PLAYER)
				summdmg *= attacker.getPlayer().getAttackCooldown();

		e.setDamage(summdmg*(1-0.00675*statDef/(1+0.00675*statDef)));
	}

}
