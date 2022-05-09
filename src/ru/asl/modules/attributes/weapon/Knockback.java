package ru.asl.modules.attributes.weapon;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ru.asl.api.bukkit.events.combat.CombatEvent;
import ru.asl.api.bukkit.events.combat.CombatEvent.CombatType;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.modules.attributes.BasicAttr;
import ru.asl.modules.attributes.ListeningCombat;

public final class Knockback extends BasicAttr implements ListeningCombat {

	public Knockback(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	@Override
	public void listen(CombatEvent e) {
		if (e.getType() != CombatType.PLAYER_TO_ENTITY && e.getType() != CombatType.PLAYER_TO_PLAYER) return;
		final EPlayer rpg = EPlayer.getEPlayer((Player)e.getAttacker());

		final Vector damagerToEntityVec = e.getReceiver().getLocation().toVector().subtract(e.getAttacker().getLocation().toVector()).normalize();

		e.getReceiver().setVelocity(damagerToEntityVec.multiply(0.025 * rpg.getStatValue(this)[0]));
	}
}
