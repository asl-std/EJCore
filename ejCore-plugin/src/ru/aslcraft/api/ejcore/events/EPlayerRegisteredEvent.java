package ru.aslcraft.api.ejcore.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import ru.aslcraft.api.ejcore.entity.EPlayer;

/**
 * <p>EPlayerRegisteredEvent class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class EPlayerRegisteredEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	/** {@inheritDoc} */
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	/**
	 * <p>getHandlerList.</p>
	 *
	 * @return a {@link org.bukkit.event.HandlerList} object
	 */
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Getter private EPlayer player;

	/**
	 * <p>Constructor for EPlayerRegisteredEvent.</p>
	 *
	 * @param who a {@link ru.aslcraft.api.ejcore.entity.EPlayer} object
	 */
	public EPlayerRegisteredEvent(EPlayer who) {
		player = who;
	}

}
