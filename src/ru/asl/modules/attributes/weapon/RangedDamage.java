package ru.asl.modules.attributes.weapon;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.events.combat.CombatEvent;
import ru.asl.api.bukkit.events.combat.CombatEvent.CombatType;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.core.Core;
import ru.asl.modules.attributes.AttrType;
import ru.asl.modules.attributes.BasicAttr;
import ru.asl.modules.attributes.ListeningCombat;

public final class RangedDamage extends BasicAttr implements ListeningCombat {

	public RangedDamage(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel, AttrType.RANGE);
	}

	@Override @SuppressWarnings("null")
	public void listen(CombatEvent e) {
		if (!e.isRanged()) return;

		double statDmg = e.getDamage();
		double statDef = 0;

		final EPlayer receiver = e.getReceiver().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getReceiver() ) : null;
		final EPlayer attacker = e.getAttacker().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getAttacker() ) : null;

		if (e.getReceiver().getType() == EntityType.PLAYER)
			statDef = receiver.getStatValue(Core.getAttr().getByKey("PROJECTILE_DEFENCE"))[0];

		if (e.getAttacker().getType() == EntityType.PLAYER)
			statDmg = (Core.getCfg().getBoolean("use-vanilla-damage", false, true) ? statDmg : 0) + attacker.getDamage(this);

		if (e.getType() == CombatType.PLAYER_TO_PLAYER) {
			statDmg = statDmg*(attacker.getStatValue(Core.getAttr().getByKey("PVP_DAMAGE_MODIFIER"))[0]/100);
			statDef = statDef*(receiver.getStatValue(Core.getAttr().getByKey("PVP_DEFENCE_MODIFIER"))[0]/100);
		}

		if (e.getType() == CombatType.PLAYER_TO_ENTITY)
			statDmg = statDmg*(attacker.getStatValue(Core.getAttr().getByKey("PVE_DAMAGE_MODIFIER"))[0]/100);

		if (e.getType() == CombatType.ENTITY_TO_PLAYER)
			statDef = statDef*(receiver.getStatValue(Core.getAttr().getByKey("PVE_DEFENCE_MODIFIER"))[0]/100);

		e.setDamage(statDmg*(1-0.00675*statDef/(1+0.00675*statDef)));
	}

}
