package ru.asl.modules.attributes.weapon;

import org.bukkit.entity.Player;

import ru.asl.api.bukkit.events.combat.CombatEvent;
import ru.asl.api.bukkit.events.combat.CombatEvent.CombatType;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.value.util.ValueUtil;
import ru.asl.modules.attributes.BasicAttr;
import ru.asl.modules.attributes.ListeningCombat;

public final class Dodge extends BasicAttr implements ListeningCombat {

	public Dodge(String keyName, String path, double base, double perLevel) {
		super(keyName, path, base, perLevel);
	}

	@Override
	public void listen(CombatEvent e) {
		if (e.getType() != CombatType.ENTITY_TO_PLAYER && e.getType() != CombatType.PLAYER_TO_PLAYER) return;
		final EPlayer rpg = EPlayer.getEPlayer((Player)e.getReceiver());

		final double absValue = rpg.getStatValue(this)[0];

		if (ValueUtil.isTrue(absValue, 100)) {
			e.setCancelled(true);
		}
	}

}
