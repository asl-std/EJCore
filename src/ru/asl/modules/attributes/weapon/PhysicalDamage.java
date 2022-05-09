package ru.asl.modules.attributes.weapon;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.events.combat.CombatEvent;
import ru.asl.api.bukkit.events.combat.CombatEvent.CombatType;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.utils.ServerVersion;
import ru.asl.core.Core;
import ru.asl.modules.attributes.AttrType;
import ru.asl.modules.attributes.BasicAttr;
import ru.asl.modules.attributes.ListeningCombat;

public final class PhysicalDamage extends BasicAttr implements ListeningCombat {

	public PhysicalDamage(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel, AttrType.RANGE);
	}

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
			statDef = receiver.getStatValue(Core.getAttr().getByKey("PHYSICAL_DEFENCE"))[0];

		if (e.getAttacker().getType() == EntityType.PLAYER) {
			statDmg = attacker.getDamage(this);

			if (!Core.getCfg().getBoolean("use-vanilla-damage", false, true))
				vanillaDmg = 0;

			if (Core.getAttr().getByKey("FIST_DAMAGE").isEnabled())
				fistDmg = attacker.getDamage(Core.getAttr().getByKey("FIST_DAMAGE"));
		}

		if (e.getType() == CombatType.PLAYER_TO_PLAYER) {
			statDmg = statDmg*(attacker.getStatValue(Core.getAttr().getByKey("PVP_DAMAGE_MODIFIER"))[0]/100);
			statDef = statDef*(receiver.getStatValue(Core.getAttr().getByKey("PVP_DEFENCE_MODIFIER"))[0]/100);
		}

		if (e.getType() == CombatType.PLAYER_TO_ENTITY)
			statDmg = statDmg*(attacker.getStatValue(Core.getAttr().getByKey("PVE_DAMAGE_MODIFIER"))[0]/100);

		if (e.getType() == CombatType.ENTITY_TO_PLAYER)
			statDef = statDef*(receiver.getStatValue(Core.getAttr().getByKey("PVE_DEFENCE_MODIFIER"))[0]/100);

		double summdmg = (fistDmg+statDmg+vanillaDmg);

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_15_2))
			if (e.getAttacker().getType() == EntityType.PLAYER)
				summdmg *= attacker.getPlayer().getAttackCooldown();

		e.setDamage(summdmg*(1-0.00675*statDef/(1+0.00675*statDef)));
	}

}
