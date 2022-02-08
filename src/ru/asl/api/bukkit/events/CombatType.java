package ru.asl.api.bukkit.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public enum CombatType {
	PLAYER_TO_PLAYER, ENTITY_TO_PLAYER, PLAYER_TO_ENTITY, ENTITY_TO_ENTITY;

	public static CombatType from(Entity receiver, Entity attacker) {
		if (attacker == null && receiver == null)
			return ENTITY_TO_ENTITY;

		if (attacker == null && receiver != null)
			if (receiver.getType() == EntityType.PLAYER) {
				return ENTITY_TO_PLAYER;
			} else
				return ENTITY_TO_ENTITY;

		if (attacker != null && receiver == null)
			if (attacker.getType() == EntityType.PLAYER) {
				return PLAYER_TO_ENTITY;
			} else
				return ENTITY_TO_ENTITY;

		if (attacker.getType() == EntityType.PLAYER && receiver.getType() == EntityType.PLAYER)
			return CombatType.PLAYER_TO_PLAYER;

		if (attacker.getType() != EntityType.PLAYER && receiver.getType() == EntityType.PLAYER)
			return CombatType.ENTITY_TO_PLAYER;

		if (attacker.getType() == EntityType.PLAYER && receiver.getType() != EntityType.PLAYER)
			return CombatType.PLAYER_TO_ENTITY;

		return CombatType.ENTITY_TO_ENTITY;
	}
}
