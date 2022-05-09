package ru.asl.api.bukkit.events.combat;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.asl.api.bukkit.events.combat.CombatEvent.CombatType;

@AllArgsConstructor
public class EntityDamagePrepareEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Getter @NonNull private Entity attacker;

	@Getter @NonNull private DamageCause cause;

	@Getter @Setter private double damage;

	@Getter @NonNull private CombatType type;

	@Getter private boolean ranged;

}
