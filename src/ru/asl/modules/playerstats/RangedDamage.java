package ru.asl.modules.playerstats;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import ru.asl.api.bukkit.events.CombatEvent;
import ru.asl.api.bukkit.events.CombatType;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.core.Core;
import ru.asl.modules.playerstats.basic.BasicAttr;
import ru.asl.modules.playerstats.basic.StatType;
import ru.asl.modules.playerstats.basic.interfaze.ListeningCombat;

public final class RangedDamage extends BasicAttr implements ListeningCombat {

	public RangedDamage(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
		type = StatType.RANGE;
	}

	@Override @SuppressWarnings("null")
	public void listen(CombatEvent e) {
		if (!e.isRanged()) return;

		double statDmg = e.getDamage();
		double statDef = 0;

		final EPlayer receiver = e.getReceiver().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getReceiver() ) : null;
		final EPlayer attacker = e.getAttacker().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getAttacker() ) : null;

		if (e.getReceiver().getType() == EntityType.PLAYER)
			statDef = receiver.getStatValue(Core.getStats().PROJECTILE_DEFENCE)[0];

		if (e.getAttacker().getType() == EntityType.PLAYER)
			statDmg = (Core.getCfg().getBoolean("use-vanilla-damage", false, true) ? statDmg : 0) + attacker.getDamage(this);

		if (e.getType() == CombatType.PLAYER_TO_PLAYER) {
			statDmg = statDmg*(attacker.getStatValue(Core.getStats().PVP_DAMAGE_MODIFIER)[0]/100);
			statDef = statDef*(receiver.getStatValue(Core.getStats().PVP_DEFENCE_MODIFIER)[0]/100);
		}

		if (e.getType() == CombatType.PLAYER_TO_ENTITY)
			statDmg = statDmg*(attacker.getStatValue(Core.getStats().PVE_DAMAGE_MODIFIER)[0]/100);

		if (e.getType() == CombatType.ENTITY_TO_PLAYER)
			statDef = statDef*(receiver.getStatValue(Core.getStats().PVE_DEFENCE_MODIFIER)[0]/100);

		e.setDamage(statDmg*(1-0.00675*statDef/(1+0.00675*statDef)));
	}

}
