package ru.asl.modules.attributes.weapon;

import org.bukkit.entity.Player;

import ru.asl.api.bukkit.events.combat.CombatEvent;
import ru.asl.api.bukkit.events.combat.CombatEvent.CombatType;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.modules.attributes.BasicAttr;
import ru.asl.modules.attributes.ListeningCombat;

public final class Lifesteal extends BasicAttr implements ListeningCombat {

	public Lifesteal(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	@Override
	public void listen(CombatEvent e) {
		if (e.getType() != CombatType.PLAYER_TO_ENTITY && e.getType() != CombatType.PLAYER_TO_PLAYER) return;
		final EPlayer rpg = EPlayer.getEPlayer((Player)e.getAttacker());

		final double absValue = rpg.getStatValue(this)[0];

		rpg.heal(e.getDamage() * (absValue/100));
	}

}
