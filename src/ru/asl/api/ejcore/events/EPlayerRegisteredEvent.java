package ru.asl.api.ejcore.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import ru.asl.api.ejcore.entity.EPlayer;

public class EPlayerRegisteredEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Getter private EPlayer player;

	public EPlayerRegisteredEvent(EPlayer who) {
		player = who;
	}

}
